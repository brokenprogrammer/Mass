#version 150

const int MAX_POINT_LIGHTS = 5;
const int MAX_SPOT_LIGHTS = 5;
const int NUM_CASCADES = 3;

in vec2 outTexCoord;
in vec3 mvVertexNormal;
in vec3 mvVertexPos;
in vec4 mlightviewVertexPos[NUM_CASCADES];
in mat4 outModelViewMatrix;
in float outSelected;

out vec4 fragColor;

struct Attenuation {
    float constant;
    float linear;
    float exponent;
};

struct PointLight {
    vec3 color;
    // Light position is within the view coordinates.
    vec3 position;
    float intensity;
    Attenuation att;
};

struct SpotLight {
	PointLight pl;
	vec3 coneDirection;
	float cutOff;
};

struct DirectionalLight {
    vec3 color;
    vec3 direction;
    float intensity;
};

struct Material {
    vec4 ambient;
    vec4 diffuse;
    vec4 specular;
    int hasTexture;
    int hasNormalMap;
    float reflectance;
};

struct Fog {
	int activeFog;
	vec3 color;
	float density;
};

uniform sampler2D texture_sampler;
uniform sampler2D normalMap;
uniform sampler2D shadowMap_0;
uniform sampler2D shadowMap_1;
uniform sampler2D shadowMap_2;
uniform vec3 ambientLight;
uniform float specularPower;
uniform Material material;
uniform PointLight pointLights[MAX_POINT_LIGHTS];
uniform SpotLight spotLights[MAX_SPOT_LIGHTS];
uniform DirectionalLight directionalLight;
uniform Fog fog;
uniform float cascadeFarPlanes[NUM_CASCADES];
uniform int renderShadow;

vec4 ambientC;
vec4 diffuseC;
vec4 specularC;

void setupColors(Material material, vec2 textCoord) {
    if (material.hasTexture == 1) {
        ambientC = texture(texture_sampler, textCoord);
        diffuseC = ambientC;
        specularC = ambientC;
    } else {
        ambientC = material.ambient;
        diffuseC = material.diffuse;
        specularC = material.specular;
    }
}

vec4 calcLightColor(vec3 lightColor, float lightIntensity, vec3 position, vec3 toLightDir, vec3 normal) {
    vec4 diffuseColor = vec4(0, 0, 0, 0);
    vec4 specularColor = vec4(0, 0, 0, 0);

    // Calculate Diffuse light.
    // Diffuse color is calculated through the following formula:
    // color = diffuseColor ∗ lColor ∗ diffuseFactor ∗ intensity
    float diffuseFactor = max(dot(normal, toLightDir), 0.0);
    diffuseColor = diffuseC * vec4(lightColor, 1.0) * lightIntensity * diffuseFactor;

    // Calculate specular light.
    // Specular color is calculated through the following formula:
    // specularColor ∗ lColor ∗ reflectance ∗ specularFactor ∗ intensity.
    vec3 camera_direction = normalize(-position);
    vec3 from_light_source = -toLightDir;
    vec3 reflected_light = normalize(reflect(from_light_source, normal));
    float specularFactor = max(dot(camera_direction, reflected_light), 0.0);
    specularFactor = pow(specularFactor, specularPower);
    specularColor = specularC * lightIntensity * specularFactor * material.reflectance * vec4(lightColor, 1.0);

    return (diffuseColor + specularColor);
}

vec4 calcPointLight(PointLight light, vec3 position, vec3 normal) {
    vec3 lightDirection = light.position - position;
    vec3 toLightDir = normalize(lightDirection);
    vec4 lightColor = calcLightColor(light.color, light.intensity, position, toLightDir, normal);

    // Calculate Attenuation.
    // The attenuation is calculated through the following formula:
    // 1.0 / (atConstant + atLinear ∗ dist + atExponent ∗ dist^2).
    float distance = length(lightDirection);
    float attenuationInv = light.att.constant + light.att.linear * distance +
        light.att.exponent * distance * distance;

    return lightColor / attenuationInv;
}

vec4 calcSpotLight(SpotLight light, vec3 position, vec3 normal) {
	vec3 lightDirection = light.pl.position - position;
	vec3 toLightDir = normalize(lightDirection);
	vec3 fromLightDir = -toLightDir;
	float spotAlfa = dot(fromLightDir, normalize(light.coneDirection));
	
	vec4 color = vec4(0, 0, 0, 0);
	
	if (spotAlfa > light.cutOff) {
		color = calcPointLight(light.pl, position, normal);
		color *= (1.0 - (1.0 - spotAlfa) / (1.0 - light.cutOff));
	}
	
	return color;
}

vec4 calcDirectionalLight(DirectionalLight light, vec3 position, vec3 normal) {
    return calcLightColor(light.color, light.intensity, position, normalize(light.direction), normal);
}

vec4 calcFog(vec3 pos, vec4 color, Fog fog, vec3 ambientLight, DirectionalLight dirLight) {
    vec3 fogColor = fog.color * (ambientLight + dirLight.color * dirLight.intensity);
    float distance = length(pos);
    float fogFactor = 1.0 / exp( (distance * fog.density)* (distance * fog.density));
    fogFactor = clamp( fogFactor, 0.0, 1.0 );

    vec3 resultColor = mix(fogColor, color.xyz, fogFactor);
    return vec4(resultColor.xyz, color.w);
}

vec3 calcNormal(Material material, vec3 normal, vec2 text_coord, mat4 modelViewMatrix) {
    vec3 newNormal = normal;
    if ( material.hasNormalMap == 1 )
    {
        newNormal = texture(normalMap, text_coord).rgb;
        newNormal = normalize(newNormal * 2 - 1);
        newNormal = normalize(modelViewMatrix * vec4(newNormal, 0.0)).xyz;
    }
    return newNormal;
}

float calcShadow(vec4 position, int idx) {
    if ( renderShadow == 0 )
    {
        return 1.0;
    }

    vec3 projCoords = position.xyz;
    // Transform from screen coordinates to texture coordinates
    projCoords = projCoords * 0.5 + 0.5;
    float bias = 0.005;

    float shadowFactor = 0.0;
    vec2 inc;
    if (idx == 0)
    {
        inc = 1.0 / textureSize(shadowMap_0, 0);
    }
    else if (idx == 1)
    {
        inc = 1.0 / textureSize(shadowMap_1, 0);
    }
    else
    {
        inc = 1.0 / textureSize(shadowMap_2, 0);
    }
    for(int row = -1; row <= 1; ++row)
    {
        for(int col = -1; col <= 1; ++col)
        {
            float textDepth;
            if (idx == 0)
            {
                textDepth = texture(shadowMap_0, projCoords.xy + vec2(row, col) * inc).r; 
            }
            else if (idx == 1)
            {
                textDepth = texture(shadowMap_1, projCoords.xy + vec2(row, col) * inc).r; 
            }
            else
            {
                textDepth = texture(shadowMap_2, projCoords.xy + vec2(row, col) * inc).r; 
            }
            shadowFactor += projCoords.z - bias > textDepth ? 1.0 : 0.0;        
        }    
    }
    shadowFactor /= 9.0;

    if(projCoords.z > 1.0)
    {
        shadowFactor = 1.0;
    }

    return 1 - shadowFactor;
}

void main() {
    setupColors(material, outTexCoord);

    vec3 currNomal = calcNormal(material, mvVertexNormal, outTexCoord, outModelViewMatrix);

    vec4 diffuseSpecularComp = calcDirectionalLight(directionalLight, mvVertexPos, currNomal);

    for (int i=0; i<MAX_POINT_LIGHTS; i++){
        if ( pointLights[i].intensity > 0 ){
            diffuseSpecularComp += calcPointLight(pointLights[i], mvVertexPos, currNomal); 
        }
    }

    for (int i=0; i<MAX_SPOT_LIGHTS; i++){
        if ( spotLights[i].pl.intensity > 0 ){
            diffuseSpecularComp += calcSpotLight(spotLights[i], mvVertexPos, currNomal);
        }
    }
    int idx;
    for (int i=0; i<NUM_CASCADES; i++){
        if ( abs(mvVertexPos.z) < cascadeFarPlanes[i] ){
            idx = i;
            break;
        }
    }
    float shadow = calcShadow(mlightviewVertexPos[idx], idx);
    fragColor = clamp(ambientC * vec4(ambientLight, 1) + diffuseSpecularComp * shadow, 0, 1);
    if ( fog.activeFog == 1 ) {
        fragColor = calcFog(mvVertexPos, fragColor, fog, ambientLight, directionalLight);
    }

    if ( outSelected > 0 ) {
        fragColor = vec4(fragColor.x, fragColor.y, 1, 1);
    }
}
