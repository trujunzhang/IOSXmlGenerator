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

@end




