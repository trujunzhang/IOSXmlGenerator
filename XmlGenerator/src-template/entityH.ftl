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
<#assign toBindType = {"Boolean":"Long", "Byte":"Long", "Short":"Long", "Int":"Long", "Long":"Long", "Float":"Double", "Double":"Double", "String":"String", "ByteArray":"Blob" }/>
<#assign toCursorType = {"Boolean":"Short", "Byte":"Short", "Short":"Short", "Int":"Int", "Long":"Long", "Float":"Float", "Double":"Double", "String":"String", "ByteArray":"Blob" }/>
<#assign complexTypes = ["String", "ByteArray", "Date"]/>

#import <Foundation/Foundation.h>

@interface ${entity.className} : NSObject

<#list entity.properties as property>
    <#if property.notNull && complexTypes?seq_contains(property.propertyType)>
    /** Not-null value. */
    </#if>
private ${property.javaType} ${property.propertyName};
</#list>

<#if entity.active>
/** Used to resolve relations */
private transient DaoSession daoSession;

/** Used for active entity operations. */
private transient ${entity.classNameDao} myDao;

    <#list entity.toOneRelations as toOne>
    private ${toOne.targetEntity.className} ${toOne.name};
        <#if toOne.useFkProperty>
        private ${toOne.resolvedKeyJavaType[0]} ${toOne.name}__resolvedKey;
        <#else>
        private boolean ${toOne.name}__refreshed;
        </#if>

    </#list>
    <#list entity.toManyRelations as toMany>
    private List<${toMany.targetEntity.className}> ${toMany.name};
    </#list>

</#if>
<#if entity.hasKeepSections>
// KEEP FIELDS - put your custom fields here
${keepFields!}    // KEEP FIELDS END

</#if>

<#if entity.constructors>
public ${entity.className}() {
}
    <#if entity.propertiesPk?has_content && entity.propertiesPk?size != entity.properties?size>

    public ${entity.className}(<#list entity.propertiesPk as
    property>${property.javaType} ${property.propertyName}<#if property_has_next>, </#if></#list>) {
        <#list entity.propertiesPk as property>
        this.${property.propertyName} = ${property.propertyName};
        </#list>
    }
    </#if>

public ${entity.className}(<#list entity.properties as
property>${property.javaType} ${property.propertyName}<#if property_has_next>, </#if></#list>) {
    <#list entity.properties as property>
    this.${property.propertyName} = ${property.propertyName};
    </#list>
}
</#if>




