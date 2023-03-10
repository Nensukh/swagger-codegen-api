package io.swagger.codegen.typescript.fetch;

import com.google.common.collect.Sets;

import io.swagger.models.properties.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.swagger.codegen.CodegenConstants;
import io.swagger.codegen.CodegenModel;
import io.swagger.codegen.CodegenProperty;
import io.swagger.codegen.DefaultCodegen;
import io.swagger.codegen.languages.TypeScriptFetchClientCodegen;
import io.swagger.models.ArrayModel;
import io.swagger.models.Model;
import io.swagger.models.ModelImpl;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("static-method")
public class TypeScriptFetchModelTest {

    @Test(description = "convert a simple TypeScript Angular model")
    public void simpleModelTest() {
        final Model model = new ModelImpl()
                .description("a sample model")
                .property("id", new LongProperty())
                .property("name", new StringProperty())
                .property("createdAt", new DateTimeProperty())
                .property("birthDate", new DateProperty())
                .required("id")
                .required("name");
        final DefaultCodegen codegen = new TypeScriptFetchClientCodegen();
        final CodegenModel cm = codegen.fromModel("sample", model);

        Assert.assertEquals(cm.name, "sample");
        Assert.assertEquals(cm.classname, "Sample");
        Assert.assertEquals(cm.description, "a sample model");
        Assert.assertEquals(cm.vars.size(), 4);

        final CodegenProperty property1 = cm.vars.get(0);
        Assert.assertEquals(property1.baseName, "id");
        Assert.assertEquals(property1.datatype, "number");
        Assert.assertEquals(property1.name, "id");
        Assert.assertEquals(property1.defaultValue, "undefined");
        Assert.assertEquals(property1.baseType, "number");
        Assert.assertTrue(property1.hasMore);
        Assert.assertTrue(property1.required);
        Assert.assertTrue(property1.isNotContainer);

        final CodegenProperty property2 = cm.vars.get(1);
        Assert.assertEquals(property2.baseName, "name");
        Assert.assertEquals(property2.datatype, "string");
        Assert.assertEquals(property2.name, "name");
        Assert.assertEquals(property2.defaultValue, "undefined");
        Assert.assertEquals(property2.baseType, "string");
        Assert.assertTrue(property2.hasMore);
        Assert.assertTrue(property2.required);
        Assert.assertTrue(property2.isNotContainer);

        final CodegenProperty property3 = cm.vars.get(2);
        Assert.assertEquals(property3.baseName, "createdAt");
        Assert.assertEquals(property3.complexType, null);
        Assert.assertEquals(property3.datatype, "Date");
        Assert.assertEquals(property3.name, "createdAt");
        Assert.assertEquals(property3.defaultValue, "undefined");
        Assert.assertTrue(property3.hasMore);
        Assert.assertFalse(property3.required);
        Assert.assertTrue(property3.isNotContainer);

        final CodegenProperty property4 = cm.vars.get(3);
        Assert.assertEquals(property4.baseName, "birthDate");
        Assert.assertEquals(property4.complexType, null);
        Assert.assertEquals(property4.datatype, "string");
        Assert.assertEquals(property4.name, "birthDate");
        Assert.assertEquals(property4.defaultValue, "undefined");
        Assert.assertFalse(property4.hasMore);
        Assert.assertFalse(property4.required);
        Assert.assertTrue(property4.isNotContainer);
    }

    @Test(description = "convert a model with list property")
    public void listPropertyTest() {
        final Model model = new ModelImpl()
                .description("a sample model")
                .property("id", new LongProperty())
                .property("urls", new ArrayProperty().items(new StringProperty()))
                .required("id");
        final DefaultCodegen codegen = new TypeScriptFetchClientCodegen();
        final CodegenModel cm = codegen.fromModel("sample", model);

        Assert.assertEquals(cm.name, "sample");
        Assert.assertEquals(cm.classname, "Sample");
        Assert.assertEquals(cm.description, "a sample model");
        Assert.assertEquals(cm.vars.size(), 2);

        final CodegenProperty property1 = cm.vars.get(0);
        Assert.assertEquals(property1.baseName, "id");
        Assert.assertEquals(property1.datatype, "number");
        Assert.assertEquals(property1.name, "id");
        Assert.assertEquals(property1.defaultValue, "undefined");
        Assert.assertEquals(property1.baseType, "number");
        Assert.assertTrue(property1.hasMore);
        Assert.assertTrue(property1.required);
        Assert.assertTrue(property1.isNotContainer);

        final CodegenProperty property2 = cm.vars.get(1);
        Assert.assertEquals(property2.baseName, "urls");
        Assert.assertEquals(property2.datatype, "Array<string>");
        Assert.assertEquals(property2.name, "urls");
        Assert.assertEquals(property2.baseType, "Array");
        Assert.assertFalse(property2.hasMore);
        Assert.assertFalse(property2.required);
        Assert.assertTrue(property2.isContainer);
    }

    @Test(description = "convert a model with complex property")
    public void complexPropertyTest() {
        final Model model = new ModelImpl()
                .description("a sample model")
                .property("children", new RefProperty("#/definitions/Children"));
        final DefaultCodegen codegen = new TypeScriptFetchClientCodegen();
        final CodegenModel cm = codegen.fromModel("sample", model);

        Assert.assertEquals(cm.name, "sample");
        Assert.assertEquals(cm.classname, "Sample");
        Assert.assertEquals(cm.description, "a sample model");
        Assert.assertEquals(cm.vars.size(), 1);

        final CodegenProperty property1 = cm.vars.get(0);
        Assert.assertEquals(property1.baseName, "children");
        Assert.assertEquals(property1.datatype, "Children");
        Assert.assertEquals(property1.name, "children");
        Assert.assertEquals(property1.defaultValue, "undefined");
        Assert.assertEquals(property1.baseType, "Children");
        Assert.assertFalse(property1.required);
        Assert.assertTrue(property1.isNotContainer);
    }

    @Test(description = "convert a model with complex list property")
    public void complexListPropertyTest() {
        final Model model = new ModelImpl()
                .description("a sample model")
                .property("children", new ArrayProperty()
                        .items(new RefProperty("#/definitions/Children")));
        final DefaultCodegen codegen = new TypeScriptFetchClientCodegen();
        final CodegenModel cm = codegen.fromModel("sample", model);

        Assert.assertEquals(cm.name, "sample");
        Assert.assertEquals(cm.classname, "Sample");
        Assert.assertEquals(cm.description, "a sample model");
        Assert.assertEquals(cm.vars.size(), 1);

        final CodegenProperty property1 = cm.vars.get(0);
        Assert.assertEquals(property1.baseName, "children");
        Assert.assertEquals(property1.complexType, "Children");
        Assert.assertEquals(property1.datatype, "Array<Children>");
        Assert.assertEquals(property1.name, "children");
        Assert.assertEquals(property1.baseType, "Array");
        Assert.assertFalse(property1.required);
        Assert.assertTrue(property1.isContainer);
    }

    @Test(description = "convert an array model")
    public void arrayModelTest() {
        final Model model = new ArrayModel()
                .description("an array model")
                .items(new RefProperty("#/definitions/Children"));
        final DefaultCodegen codegen = new TypeScriptFetchClientCodegen();
        final CodegenModel cm = codegen.fromModel("sample", model);

        Assert.assertEquals(cm.name, "sample");
        Assert.assertEquals(cm.classname, "Sample");
        Assert.assertEquals(cm.description, "an array model");
        Assert.assertEquals(cm.vars.size(), 0);
    }

    @Test(description = "convert a map model")
    public void mapModelTest() {
        final Model model = new ModelImpl()
                .description("a map model")
                .additionalProperties(new RefProperty("#/definitions/Children"));
        final DefaultCodegen codegen = new TypeScriptFetchClientCodegen();
        final CodegenModel cm = codegen.fromModel("sample", model);

        Assert.assertEquals(cm.name, "sample");
        Assert.assertEquals(cm.classname, "Sample");
        Assert.assertEquals(cm.description, "a map model");
        Assert.assertEquals(cm.vars.size(), 0);
        Assert.assertEquals(cm.imports.size(), 1);
        Assert.assertEquals(Sets.intersection(cm.imports, Sets.newHashSet("Children")).size(), 1);
    }

    @Test(description = "test enum var names respect capitalisation option 'enumPropertyNaming'")
    public void enumVarNameCapitalisationTest() {
        final List<String> enumValues = Arrays.asList("camelCase", "PascalCase", "snake_case", "SHOUTY_CASE");

        Assert.assertEquals(getEnumVarNames(null, enumValues), Arrays.asList("CamelCase", "PascalCase", "SnakeCase", "SHOUTYCASE"));
        Assert.assertEquals(getEnumVarNames("camelCase", enumValues), Arrays.asList("camelCase", "pascalCase", "snakeCase", "sHOUTYCASE"));
        Assert.assertEquals(getEnumVarNames("PascalCase", enumValues), Arrays.asList("CamelCase", "PascalCase", "SnakeCase", "SHOUTYCASE"));
        Assert.assertEquals(getEnumVarNames("snake_case", enumValues), Arrays.asList("camel_case", "pascal_case", "snake_case", "shouty_case"));
        Assert.assertEquals(getEnumVarNames("original", enumValues), enumValues);
        Assert.assertEquals(getEnumVarNames("UPPERCASE", enumValues), Arrays.asList("CAMELCASE", "PASCALCASE", "SNAKE_CASE", "SHOUTY_CASE"));

        //IMPORTANT: these are not final enum values, which may be further updated
        //by postProcessModels
    }

    private static List<String> getEnumVarNames(final String enumPropertyNaming, final List<String> enumValues) {
        final DefaultCodegen codegen = new TypeScriptFetchClientCodegen();
        if (enumPropertyNaming != null) {
            codegen.additionalProperties().put(CodegenConstants.ENUM_PROPERTY_NAMING, enumPropertyNaming);
        }
        codegen.processOpts();

        final StringProperty propInput = new StringProperty();
        propInput.setEnum(enumValues);
        final CodegenProperty propOutput = codegen.fromProperty("prop1", propInput);
        codegen.updateCodegenPropertyEnum(propOutput);

        final List<String> enumVarNames = new ArrayList<String>();
        for (Object enumVar : (Collection<?>) propOutput.allowableValues.get("enumVars")) {
            enumVarNames.add((String) ((Map<?, ?>) enumVar).get("name"));
        }
        return enumVarNames;
    }

    @Test(description = "test enum array model")
    public void enumArrayMdoelTest() {
        final Swagger model = new SwaggerParser().read("src/test/resources/2_0/petstore-with-fake-endpoints-models-for-testing.yaml");
        final DefaultCodegen codegen = new TypeScriptFetchClientCodegen();
        final Model definition = model.getDefinitions().get("EnumArrays");

        Property property = definition.getProperties().get("array_enum");
        CodegenProperty prope = codegen.fromProperty("array_enum", property);
        codegen.updateCodegenPropertyEnum(prope);
        Assert.assertEquals(prope.datatypeWithEnum, "Array<ArrayEnumEnum>");
        Assert.assertEquals(prope.enumName, "ArrayEnumEnum");
        Assert.assertTrue(prope.isEnum);
        Assert.assertEquals(prope.allowableValues.get("values"), Arrays.asList("fish", "crab"));

        Map<String, String> fish = nameValuePair("Fish", "'fish'");
        Map<String, String> crab = nameValuePair("Crab", "'crab'");
        Assert.assertEquals(prope.allowableValues.get("enumVars"), Arrays.asList(fish, crab));

        // assert inner items
        Assert.assertEquals(prope.datatypeWithEnum, "Array<ArrayEnumEnum>");
        Assert.assertEquals(prope.enumName, "ArrayEnumEnum");
        Assert.assertTrue(prope.items.isEnum);
        Assert.assertEquals(prope.items.allowableValues.get("values"), Arrays.asList("fish", "crab"));
        Assert.assertEquals(prope.items.allowableValues.get("enumVars"), Arrays.asList(fish, crab));

        //IMPORTANT: these are not final enum values, which may be further updated
        //by postProcessModels

    }

    @Test(description = "test enum model for values (numeric, string, etc)")
    public void enumMdoelValueTest() {
        final Swagger model = new SwaggerParser().read("src/test/resources/2_0/petstore-with-fake-endpoints-models-for-testing.yaml");
        final DefaultCodegen codegen = new TypeScriptFetchClientCodegen();
        final Model definition = model.getDefinitions().get("Enum_Test");

        Property property = definition.getProperties().get("enum_integer");
        CodegenProperty prope = codegen.fromProperty("enum_integer", property);
        codegen.updateCodegenPropertyEnum(prope);
        Assert.assertEquals(prope.datatypeWithEnum, "EnumIntegerEnum");
        Assert.assertEquals(prope.enumName, "EnumIntegerEnum");
        Assert.assertTrue(prope.isEnum);
        Assert.assertFalse(prope.isContainer);
        Assert.assertNull(prope.items);
        Assert.assertEquals(prope.allowableValues.get("values"), Arrays.asList(1, -1));

        Map<String, String> one = nameValuePair("NUMBER_1", "1");
        Map<String, String> minusOne = nameValuePair("NUMBER_MINUS_1", "-1");
        Assert.assertEquals(prope.allowableValues.get("enumVars"), Arrays.asList(one, minusOne));

        //IMPORTANT: these are not final enum values, which may be further updated
        //by postProcessModels
    }

    private static Map<String, String> nameValuePair(final String name, final String value) {
        Map<String, String> result = new LinkedHashMap<String, String>();
        result.put("name", name);
        result.put("value", value);
        return result;
    }

}
