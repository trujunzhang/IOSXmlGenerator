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
    GHAssertEqualObjects(${entity.classVariable}.${property.propertyName},  @"${property.propertyValue}", MESSAGE_EQUAL, ${entity.classVariable}.${property.propertyName},  @"${property.propertyValue}");
</#list>
<#if entity.active>
    <#list entity.toOneRelations as toOne>
    [self check${toOne.targetEntity.className}:${entity.classVariable}.${toOne.name}];
    </#list>
    <#list entity.toManyRelations as toMany>
    //  class:${toMany.targetEntity.className}
    if ([${entity.classVariable}.${toMany.name} count] > 0) {
      [self check${toMany.targetEntity.className}:${entity.classVariable}.${toMany.name}[0]];
    }
    </#list>
</#if>
}

@end
