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
    float intesity;
    Attenuation att;
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

vec4 ambientC;
vec4 diffuseC;
vec4 specularC;

void setupColors(Material material, vec2 textCoord) {
    if (material.hasTexture == 1) {
        ambientC = texture(texture_sampler, outTexCoord);
        diffuseC = ambientC;
        specularC = ambientC;
    } else {
        ambientC = material.ambient;
        diffuseC = material.diffuse;
        specularC = material.specular;
    }
}

vec4 calcPointLight(PointLight light, vec3 position, vec3 normal) {
    vec4 diffuseColor = vec4(0, 0, 0, 0);
    vec4 specularColor = vec4(0, 0, 0, 0);

    // Calculate Diffuse light.
    // Diffuse color is calculated through the following formula:
    // colour = diffuseColour ∗ lColour ∗ diffuseFactor ∗ intensity
    vec3 light_direction = light.position - position;
    vec3 to_light_source = normalize(light_direction);
    float diffuseFactor = max(dot(normal, to_light_source), 0.0);
    diffuseColor = diffuseC * vec4(light.color, 1.0) * light.intesity * diffuseColor;

    // Calculate specular light.
    // Specular color is calculated through the following formula:
    // specularColour ∗ lColour ∗ reflectance ∗ specularFactor ∗ intensity.
    vec3 camera_direction = normalize(-position);
    vec3 from_light_source = -to_light_source;
    vec3 reflected_light = normalize(reflect(from_light_source, normal));
    float specularFactor = max(dot(camera_direction, reflected_light), 0.0);
    specularFactor = pow(specularFactor, specularPower);
    specularColor = specularC * specularFactor * material.reflectance * vec4(light.color, 1.0);

    // Calculate Attenuation.
    // The attenuation is calculated through the following formula:
    // 1.0 / (atConstant + atLinear ∗ dist + atExponent ∗ dist^2).
    float distance = length(light_direction);
    float attenuationInv = light.att.constant + light.att.linear * distance +
        light.att.exponent * distance * distance;

    return (diffuseColor + specularColor) / attenuationInv;
}

void main() {
    setupColors(material, outTexCoord);

    vec4 diffuseSpecularComp = calcPointLight(pointLight, vertexPos, vertexNormal);

    fragColor = ambientC * vec4(ambientLight, 1) + diffuseSpecularComp;
}
