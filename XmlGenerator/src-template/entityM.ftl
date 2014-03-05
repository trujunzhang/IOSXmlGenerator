<#--

Copyright (C) 2011 Markus Junginger, greenrobot (http://greenrobot.de)     
                                                                           
This file is part of greenDAO Generator.                                   
                                                                           
greenDAO Generator is free software: you can redistribute it and/or modify 
it under the terms of the GNU General Public License as published by       
the Free Software Foundation, either version 3 of the License, or          
(at your option) any later version.                                        
greenDAO Generator is distributed in the hope that it will be useful,      
but WITHOUT ANY WARRANTY; without even the implied warranty of             
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the              
GNU General Public License for more details.                               
                                                                           
You should have received a copy of the GNU General Public License          
along with greenDAO Generator.  If not, see <http://www.gnu.org/licenses/>.

-->
<#assign toBindType = {"Boolean":"Long", "Byte":"Long", "Short":"Long", "Int":"Long", "Long":"Long", "Float":"Double", "Double":"Double", "String":"Wanghao", "ByteArray":"Blob" }/>
<#assign toCursorType = {"Boolean":"Short", "Byte":"Short", "Short":"Short", "Int":"Int", "Long":"Long", "Float":"Float", "Double":"Double", "String":"Wanghao", "ByteArray":"Blob" }/>
<#assign complexTypes = ["String", "ByteArray", "Date"]/>

#import "${entity.className}.h"

@implementation ${entity.className}

<#if entity.constructors>
- (id)<#list entity.properties as property><#if property_index==0>init<#else>${property.propertyName}</#if>:(${property.javaType} *)${property.propertyName}<#if property_has_next> </#if></#list>{
    if ((self = [super init])) {
        <#list entity.properties as property>
        self.${property.propertyName} = ${property.propertyName}<#if property_has_next> </#if>;
        </#list>
    }
</#if>
    return self;
}

<#if entity.active>
    <#list entity.toOneRelations as toOne>
    </#list>
    <#list entity.toManyRelations as toMany>
- (id)init {
   if ((self = [super init])) {
      self.${toMany.name} = [[NSMutableArray alloc] init];
   }
   return self;
}
    </#list>
</#if>


- (void)save${entity.className}:(${entity.className} *)info {
   NSUserDefaults * settings = [NSUserDefaults standardUserDefaults];
<#list entity.properties as property>
    <#if property.notNull && complexTypes?seq_contains(property.propertyType)></#if>
   [settings removeObjectForKey:@"${property.propertyName}"];
</#list>
<#list entity.properties as property>
    <#if property.notNull && complexTypes?seq_contains(property.propertyType)></#if>
   [settings setObject:info.${property.propertyName} forKey:@"${property.propertyName}"];
</#list>
   [settings synchronize];
}

- (${entity.className} *)get${entity.className} {
   NSUserDefaults * settings = [NSUserDefaults standardUserDefaults];
   ${entity.className} * info = [[${entity.className} alloc] init];
<#list entity.properties as property>
    <#if property.notNull && complexTypes?seq_contains(property.propertyType)></#if>
   info.${property.propertyName}=[settings objectForKey:@"${property.propertyName}"];
</#list>
   return info;
}




@end




