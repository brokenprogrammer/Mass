#version 150

in vec2 outTexCoord;
in vec3 vertexNormal;
in vec3 vertexPos;

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
    float reflectance;
};

uniform sampler2D texture_sampler;
uniform vec3 ambientLight;
uniform float specularPower;
uniform Material material;
uniform PointLight pointLight;
uniform SpotLight spotLight;
uniform DirectionalLight directionalLight;

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
    // colour = diffuseColour ∗ lColour ∗ diffuseFactor ∗ intensity
    float diffuseFactor = max(dot(normal, toLightDir), 0.0);
    diffuseColor = diffuseC * vec4(lightColor, 1.0) * lightIntensity * diffuseFactor;

    // Calculate specular light.
    // Specular color is calculated through the following formula:
    // specularColour ∗ lColour ∗ reflectance ∗ specularFactor ∗ intensity.
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

void main() {
    setupColors(material, outTexCoord);

    vec4 diffuseSpecularComp = calcDirectionalLight(directionalLight, vertexPos, vertexNormal);
    diffuseSpecularComp += calcPointLight(pointLight, vertexPos, vertexNormal);
    diffuseSpecularComp += calcSpotLight(spotLight, vertexPos, vertexNormal);

    fragColor = ambientC * vec4(ambientLight, 1) + diffuseSpecularComp;
}
