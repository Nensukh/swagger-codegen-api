package io.swagger.codegen.objc;

import io.swagger.codegen.*;
import io.swagger.codegen.languages.ObjcClientCodegen;
import io.swagger.models.*;
import io.swagger.models.properties.*;
import io.swagger.parser.SwaggerParser;

import com.google.common.collect.Sets;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

@SuppressWarnings("static-method")
public class ObjcModelTest {

    @Test(description = "convert a model with a advanced map property")
    public void advancedMapPropertyTest() {
        final Model model = new ModelImpl()
                .description("a sample model")
                .property("translations", new MapProperty()
                        .additionalProperties(new MapProperty().additionalProperties(new StringProperty())))
                .required("id");
        final DefaultCodegen codegen = new ObjcClientCodegen();
        final CodegenModel cm = codegen.fromModel("sample", model);

        Assert.assertEquals(cm.name, "sample");
        Assert.assertEquals(cm.classname, "SWGSample");
        Assert.assertEquals(cm.description, "a sample model");
        Assert.assertEquals(cm.vars.size(), 1);

        final CodegenProperty property1 = cm.vars.get(0);
        Assert.assertEquals(property1.baseName, "translations");
        Assert.assertEquals(property1.datatype, "NSDictionary<NSString*, NSDictionary<NSString*, NSString*>*>*");
        Assert.assertEquals(property1.name, "translations");
        Assert.assertEquals(property1.baseType, "NSDictionary");
        Assert.assertEquals(property1.containerType, "map");
        Assert.assertFalse(property1.required);
        Assert.assertTrue(property1.isContainer);
    }

    @Test(description = "convert a simple java model")
    public void simpleModelTest() {
        final Model model = new ModelImpl()
                .description("a sample model")
                .property("id", new LongProperty())
                .property("name", new StringProperty())
                .property("createdAt", new DateTimeProperty())
                .required("id")
                .required("name")
                .discriminator("test");
        final DefaultCodegen codegen = new ObjcClientCodegen();
        final CodegenModel cm = codegen.fromModel("sample", model);

        Assert.assertEquals(cm.name, "sample");
        Assert.assertEquals(cm.classname, "SWGSample");
        Assert.assertEquals(cm.description, "a sample model");
        Assert.assertEquals(cm.vars.size(), 3);
        Assert.assertEquals(cm.discriminator, "test");

        final CodegenProperty property1 = cm.vars.get(0);
        Assert.assertEquals(property1.baseName, "id");
        Assert.assertEquals(property1.datatype, "NSNumber*");
        Assert.assertEquals(property1.name, "_id");
        Assert.assertNull(property1.defaultValue);
        Assert.assertEquals(property1.baseType, "NSNumber");
        Assert.assertTrue(property1.hasMore);
        Assert.assertTrue(property1.required);
        Assert.assertTrue(property1.isPrimitiveType);
        Assert.assertTrue(property1.isNotContainer);

        final CodegenProperty property2 = cm.vars.get(1);
        Assert.assertEquals(property2.baseName, "name");
        Assert.assertEquals(property2.datatype, "NSString*");
        Assert.assertEquals(property2.name, "name");
        Assert.assertNull(property2.defaultValue);
        Assert.assertEquals(property2.baseType, "NSString");
        Assert.assertTrue(property2.hasMore);
        Assert.assertTrue(property2.required);
        Assert.assertTrue(property2.isPrimitiveType);
        Assert.assertTrue(property2.isNotContainer);

        final CodegenProperty property3 = cm.vars.get(2);
        Assert.assertEquals(property3.baseName, "createdAt");
        Assert.assertEquals(property3.datatype, "NSDate*");
        Assert.assertEquals(property3.name, "createdAt");
        Assert.assertNull(property3.defaultValue);
        Assert.assertEquals(property3.baseType, "NSDate");
        Assert.assertFalse(property3.hasMore);
        Assert.assertFalse(property3.required);
        Assert.assertTrue(property3.isNotContainer);
    }

    @Test(description = "convert a model with list property")
    public void listPropertyTest() {
        final Model model = new ModelImpl()
                .description("a sample model")
                .property("id", new LongProperty())
                .property("urls", new ArrayProperty()
                        .items(new StringProperty()))
                .required("id");
        final DefaultCodegen codegen = new ObjcClientCodegen();
        final CodegenModel cm = codegen.fromModel("sample", model);

        Assert.assertEquals(cm.name, "sample");
        Assert.assertEquals(cm.classname, "SWGSample");
        Assert.assertEquals(cm.description, "a sample model");
        Assert.assertEquals(cm.vars.size(), 2);

        final CodegenProperty property1 = cm.vars.get(0);
        Assert.assertEquals(property1.baseName, "id");
        Assert.assertEquals(property1.datatype, "NSNumber*");
        Assert.assertEquals(property1.name, "_id");
        Assert.assertNull(property1.defaultValue);
        Assert.assertEquals(property1.baseType, "NSNumber");
        Assert.assertTrue(property1.hasMore);
        Assert.assertTrue(property1.required);
        Assert.assertTrue(property1.isPrimitiveType);
        Assert.assertTrue(property1.isNotContainer);

        final CodegenProperty property2 = cm.vars.get(1);
        Assert.assertEquals(property2.baseName, "urls");
        Assert.assertEquals(property2.datatype, "NSArray<NSString*>*");
        Assert.assertEquals(property2.name, "urls");
        Assert.assertNull(property2.defaultValue);
        Assert.assertEquals(property2.baseType, "NSArray");
        Assert.assertFalse(property2.hasMore);
        Assert.assertEquals(property2.containerType, "array");
        Assert.assertFalse(property2.required);
        Assert.assertTrue(property2.isPrimitiveType);
        Assert.assertTrue(property2.isContainer);
    }

    @Test(description = "convert a model with a map property")
    public void mapPropertyTest() {
        final Model model = new ModelImpl()
                .description("a sample model")
                .property("translations", new MapProperty()
                        .additionalProperties(new StringProperty()))
                .required("id");
        final DefaultCodegen codegen = new ObjcClientCodegen();
        final CodegenModel cm = codegen.fromModel("sample", model);

        Assert.assertEquals(cm.name, "sample");
        Assert.assertEquals(cm.classname, "SWGSample");
        Assert.assertEquals(cm.description, "a sample model");
        Assert.assertEquals(cm.vars.size(), 1);

        final CodegenProperty property1 = cm.vars.get(0);
        Assert.assertEquals(property1.baseName, "translations");
        Assert.assertEquals(property1.datatype, "NSDictionary<NSString*, NSString*>*");
        Assert.assertEquals(property1.name, "translations");
        Assert.assertEquals(property1.baseType, "NSDictionary");
        Assert.assertEquals(property1.containerType, "map");
        Assert.assertFalse(property1.required);
        Assert.assertTrue(property1.isContainer);
        Assert.assertTrue(property1.isPrimitiveType);
    }


    @Test(description = "convert a model with complex property")
    public void complexPropertyTest() {
        final Model model = new ModelImpl()
                .description("a sample model")
                .property("children", new RefProperty("#/definitions/Children"));
        final DefaultCodegen codegen = new ObjcClientCodegen();
        final CodegenModel cm = codegen.fromModel("sample", model);

        Assert.assertEquals(cm.name, "sample");
        Assert.assertEquals(cm.classname, "SWGSample");
        Assert.assertEquals(cm.description, "a sample model");
        Assert.assertEquals(cm.vars.size(), 1);

        final CodegenProperty property1 = cm.vars.get(0);
        Assert.assertEquals(property1.baseName, "children");
        Assert.assertEquals(property1.datatype, "SWGChildren*");
        Assert.assertEquals(property1.name, "children");
        Assert.assertEquals(property1.baseType, "SWGChildren");
        Assert.assertFalse(property1.required);
        Assert.assertTrue(property1.isNotContainer);
    }

    @Test(description = "convert a model with complex list property")
    public void complexListPropertyTest() {
        final Model model = new ModelImpl()
                .description("a sample model")
                .property("children", new ArrayProperty()
                        .items(new RefProperty("#/definitions/Children")));
        final DefaultCodegen codegen = new ObjcClientCodegen();
        final CodegenModel cm = codegen.fromModel("sample", model);

        Assert.assertEquals(cm.name, "sample");
        Assert.assertEquals(cm.classname, "SWGSample");
        Assert.assertEquals(cm.description, "a sample model");
        Assert.assertEquals(cm.vars.size(), 1);

        final CodegenProperty property1 = cm.vars.get(0);
        Assert.assertEquals(property1.baseName, "children");
        Assert.assertEquals(property1.complexType, "SWGChildren");
        Assert.assertEquals(property1.datatype, "NSArray<SWGChildren>*");
        Assert.assertEquals(property1.name, "children");
        Assert.assertEquals(property1.baseType, "NSArray");
        Assert.assertEquals(property1.containerType, "array");
        Assert.assertFalse(property1.required);
        Assert.assertTrue(property1.isContainer);
    }

    @Test(description = "convert a model with complex map property")
    public void complexMapPropertyTest() {
        final Model model = new ModelImpl()
                .description("a sample model")
                .property("children", new MapProperty()
                        .additionalProperties(new RefProperty("#/definitions/Children")));
        final DefaultCodegen codegen = new ObjcClientCodegen();
        final CodegenModel cm = codegen.fromModel("sample", model);

        Assert.assertEquals(cm.name, "sample");
        Assert.assertEquals(cm.classname, "SWGSample");
        Assert.assertEquals(cm.description, "a sample model");
        Assert.assertEquals(cm.vars.size(), 1);
        Assert.assertEquals(Sets.intersection(cm.imports, Sets.newHashSet("SWGChildren")).size(), 1);

        final CodegenProperty property1 = cm.vars.get(0);
        Assert.assertEquals(property1.baseName, "children");
        Assert.assertEquals(property1.complexType, "SWGChildren");
        Assert.assertEquals(property1.datatype, "NSDictionary<SWGChildren>*");
        Assert.assertEquals(property1.name, "children");
        Assert.assertEquals(property1.baseType, "NSDictionary");
        Assert.assertEquals(property1.containerType, "map");
        Assert.assertFalse(property1.required);
        Assert.assertTrue(property1.isContainer);
        Assert.assertFalse(property1.isNotContainer);
    }

    @Test(description = "convert an array model")
    public void arrayModelTest() {
        final Model model = new ArrayModel()
                .description("an array model")
                .items(new RefProperty("#/definitions/Children"));
        final DefaultCodegen codegen = new ObjcClientCodegen();
        final CodegenModel cm = codegen.fromModel("sample", model);

        Assert.assertEquals(cm.name, "sample");
        Assert.assertEquals(cm.classname, "SWGSample");
        Assert.assertEquals(cm.description, "an array model");
        Assert.assertEquals(cm.vars.size(), 0);
        Assert.assertEquals(cm.parent, "NSMutableArray");
        Assert.assertEquals(cm.imports.size(), 1);
        Assert.assertEquals(Sets.intersection(cm.imports, Sets.newHashSet("SWGChildren")).size(), 1);
    }

    @Test(description = "convert an map model")
    public void mapModelTest() {
        final Model model = new ModelImpl()
                .description("a map model")
                .additionalProperties(new RefProperty("#/definitions/Children"));
        final DefaultCodegen codegen = new ObjcClientCodegen();
        final CodegenModel cm = codegen.fromModel("sample", model);

        Assert.assertEquals(cm.name, "sample");
        Assert.assertEquals(cm.classname, "SWGSample");
        Assert.assertEquals(cm.description, "a map model");
        Assert.assertEquals(cm.vars.size(), 0);
        Assert.assertEquals(cm.parent, "NSMutableDictionary");
        Assert.assertEquals(cm.imports.size(), 1);
        Assert.assertEquals(Sets.intersection(cm.imports, Sets.newHashSet("SWGChildren")).size(), 1);
    }

    @Test(description = "test udid")
    public void udidAndPasswordDataModelTest() {
        final Swagger model = new SwaggerParser().read("src/test/resources/2_0/petstore-with-fake-endpoints-models-for-testing.yaml");
        final DefaultCodegen codegen = new ObjcClientCodegen();
        final Model definition = model.getDefinitions().get("format_test");

        Property property = definition.getProperties().get("uuid");
        CodegenProperty prope = codegen.fromProperty("uuid", property);
        Assert.assertEquals(prope.baseType, "NSString");

        prope = codegen.fromProperty("password", property);
        Assert.assertEquals(prope.baseType, "NSString");
    }

    @Test(description = "test mixedProperties")
    public void mixedPropertiesDataModelTest() {
        final Swagger model = new SwaggerParser().read("src/test/resources/2_0/petstore-with-fake-endpoints-models-for-testing.yaml");
        final DefaultCodegen codegen = new ObjcClientCodegen();
        final Model definition = model.getDefinitions().get("MixedPropertiesAndAdditionalPropertiesClass");

        Property property = definition.getProperties().get("map");
        CodegenProperty prope = codegen.fromProperty("map", property);
        Assert.assertEquals(prope.baseType, "NSDictionary");
    }

    @Test(description = "test isArrayModel")
    public void isArrayModelModelTest() {
        final Swagger model = new SwaggerParser().read("src/test/resources/2_0/petstore-with-fake-endpoints-models-for-testing.yaml");
        final DefaultCodegen codegen = new ObjcClientCodegen();
        final Model definition = model.getDefinitions().get("AnimalFarm");
        final CodegenModel codegenModel = codegen.fromModel("AnimalFarm", definition);

        Assert.assertEquals(codegenModel.isArrayModel, true);
        Assert.assertEquals(codegenModel.arrayModelType, "SWGAnimal");
    }


    @Test(description = "test binary data")
    public void binaryDataModelTest() {
        final Swagger model = new SwaggerParser().read("src/test/resources/2_0/binaryDataTest.json");
        final DefaultCodegen codegen = new ObjcClientCodegen();
        final String path = "/tests/binaryResponse";
        final Operation p = model.getPaths().get(path).getPost();
        final CodegenOperation op = codegen.fromOperation(path, "post", p, model.getDefinitions());

        Assert.assertEquals(op.returnType, "NSData*");
        Assert.assertEquals(op.bodyParam.dataType, "NSData*");
        Assert.assertTrue(op.bodyParam.isBinary);
        Assert.assertTrue(op.responses.get(0).isBinary);
    }

    @Test(description = "create proper imports per #316")
    public void issue316Test() {
        final Swagger model = new SwaggerParser().read("src/test/resources/2_0/postBodyTest.json");
        final DefaultCodegen codegen = new ObjcClientCodegen();

        final Map<String, Path> animalPaths = model.getPaths();

        final Path animalOps = animalPaths.get("/animals");
        Assert.assertNotNull(animalOps.getPost());

        final CodegenOperation animalCo = codegen.fromOperation("/animals", "POST", animalOps.getPost(), model.getDefinitions());
        Assert.assertEquals(animalCo.imports.size(), 1);
        Assert.assertTrue(animalCo.imports.contains("SWGAnimal"));

        final Map<String, Path> insectPaths = model.getPaths();
        final Path insectOps = insectPaths.get("/insects");
        Assert.assertNotNull(insectOps.getPost());

        final CodegenOperation insectCo = codegen.fromOperation("/insects", "POST", insectOps.getPost(), model.getDefinitions());
        Assert.assertEquals(insectCo.imports.size(), 1);
        Assert.assertTrue(insectCo.imports.contains("SWGInsect"));
    }
}
