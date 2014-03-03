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


#pragma mark - read for ${entity.className}
+ (${entity.className} *)read${entity.className}:(NSString *)str {
   TBXMLElement * root = [[TBXML alloc] initWithXMLString:str error:nil].rootXMLElement;

   return [self readFor${entity.className}:root];
}



+ (${entity.className} *)readFor${entity.className}:(TBXMLElement *)pElement {
   ${entity.className} * ${entity.classVariable} = [[${entity.className} alloc] init];

<#list entity.properties as property>
   //  ${property.propertyName}
   TBXMLElement * ${property.propertyName}s = [TBXML childElementNamed:@"${property.propertyName}" parentElement:pElement];
   if (${property.propertyName}s) {
      ${entity.classVariable}.${property.propertyName} = [TBXML textForElement:${property.propertyName}s];
   }
</#list>

<#if entity.active>
   <#list entity.toOneRelations as toOne>
   TBXMLElement * ${toOne.name}s = [TBXML childElementNamed:@"${toOne.name}" parentElement:pElement];
   if (${toOne.name}s) {
      ${entity.classVariable}.${toOne.name} = [self readFor${toOne.targetEntity.className}:${toOne.name}s];
   }
   </#list>
   <#list entity.toManyRelations as toMany>
   //  NSMutableArray:[class]
   NSString * arrayTag = @"com.xinma.xmobile.travel.model.TravelPlan";
   TBXMLElement * elementList = [TBXML childElementNamed:@"list" parentElement:pElement];
   while (elementList) {
      TBXMLElement * elementClass = [TBXML childElementNamed:arrayTag parentElement:elementList];
      while (elementClass) {
      ${toMany.targetEntity.className} * model = [self readFor${toMany.targetEntity.className}:elementClass];
         [${entity.classVariable}.${toMany.name} addObject:model];
         elementClass = [TBXML nextSiblingNamed:arrayTag searchFromElement:elementClass];
      }
      elementList = [TBXML nextSiblingNamed:@"list" searchFromElement:elementList];
   }

   </#list>
</#if>

   return ${entity.classVariable};
}

