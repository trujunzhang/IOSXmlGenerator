//
//  ${entity.className}Test.m
//
#import <GHUnit/GHUnit.h>

#import "ParserTools.h"


@interface ${entity.className}Test : GHTestCase
@end


@implementation ${entity.className}Test

#pragma mark - test ${entity.className}
- (void)test${entity.className} {
   NSString * xmlPath = [[NSBundle mainBundle] pathForResource:@"${entity.className}" ofType:@"xml"];
   ${entity.className} * ${entity.classVariable} = [ParserTools read${entity.className}:[NSString stringWithContentsOfFile:xmlPath]];
   [self check${entity.className}:${entity.classVariable}];
}


- (void)check${entity.className}:(${entity.className} *)${entity.classVariable} {

<#list entity.properties as property>
    //  ${property.propertyName}
    NSString * ${property.propertyName} = ${entity.classVariable}.${property.propertyName};
    NSString * ${property.propertyName}Expected = @"${property.propertyValue}";
    GHAssertEqualObjects(${property.propertyName}, ${property.propertyName}Expected, @"A custom error message. %@ should be equal to: %@.", ${property.propertyName}, ${property.propertyName}Expected);
</#list>

<#if entity.active>
    <#list entity.toOneRelations as toOne>
    [self check${toOne.targetEntity.className}:${entity.classVariable}.${toOne.name}];
    </#list>
    <#list entity.toManyRelations as toMany>
    [self check${toMany.targetEntity.className}:${entity.classVariable}.${toMany.name}];
    </#list>
</#if>

}

@end
