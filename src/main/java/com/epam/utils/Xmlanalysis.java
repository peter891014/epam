package com.epam.utils;/*
 *@title Xmlanalysis
 *@description
 *@author ASUS
 *@create 2023/8/10 13:13
 */

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.*;

/**
 * @program: epam
 * @description:
 * @author: 作者名字
 * @create: 2023-08-10 13:13
 **/
public class Xmlanalysis {
    public static void main(String[] args) throws DocumentException { String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><bpmn2:definitions xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:bpmn2=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:dc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:di=\"http://www.omg.org/spec/DD/20100524/DI\" xmlns:flowable=\"http://flowable.org/bpmn\" id=\"diagram_Flow_1691566748947\" targetNamespace=\"http://bpmn.io/schema/bpmn\"> <bpmn2:process id=\"Flow_1691566748947\" name=\"手动创建工单\" isExecutable=\"true\" flowable:processCategory=\"zdyyw\">   <bpmn2:startEvent id=\"Event_07er7bi\" name=\"开始\">     <bpmn2:extensionElements>" +
            "        <flowable:formData />      </bpmn2:extensionElements>     <bpmn2:outgoing>Flow_1yz8x9c</bpmn2:outgoing>   </bpmn2:startEvent>   <bpmn2:userTask id=\"Activity_1cplga5\" name=\"处理人\" flowable:assignee=\"${INITIATOR}\">     <bpmn2:extensionElements>       <flowable:formData />     </bpmn2:extensionElements>     <bpmn2:incoming>Flow_1yz8x9c</bpmn2:incoming>     <bpmn2:outgoing>Flow_0x0rsj4</bpmn2:outgoing>   </bpmn2:userTask>   <bpmn2:userTask id=\"Activity_1ut4c1h\" name=\"审核人\" flowable:candidateGroups=\"1681915877128245249\">" +
            "      <bpmn2:extensionElements>        <flowable:formData />     </bpmn2:extensionElements>     <bpmn2:incoming>Flow_0x0rsj4</bpmn2:incoming>     <bpmn2:outgoing>Flow_017zdk2</bpmn2:outgoing>   </bpmn2:userTask>   <bpmn2:endEvent id=\"Event_1nxfq2q\" name=\"结束\">     <bpmn2:incoming>Flow_017zdk2</bpmn2:incoming>   </bpmn2:endEvent>   <bpmn2:sequenceFlow id=\"Flow_1yz8x9c\" sourceRef=\"Event_07er7bi\" targetRef=\"Activity_1cplga5\" />   <bpmn2:sequenceFlow id=\"Flow_0x0rsj4\" sourceRef=\"Activity_1cplga5\" targetRef=\"Activity_1ut4c1h\" />   <bpmn2:sequenceFlow id=\"Flow_017zdk2\" sourceRef=\"Activity_1ut4c1h\" targetRef=\"Event_1nxfq2q\" /> </bpmn2:process> <bpmndi:BPMNDiagram id=\"BPMNDiagram_1\">   <bpmndi:BPMNPlane id=\"BPMNPlane_1\" bpmnElement=\"Flow_1691566748947\">     <bpmndi:BPMNEdge id=\"Flow_017zdk2_di\" bpmnElement=\"Flow_017zdk2\">       <di:waypoint x=\"760\" y=\"200\" />       <di:waypoint x=\"932\" y=\"200\" />     </bpmndi:BPMNEdge>     <bpmndi:BPMNEdge id=\"Flow_0x0rsj4_di\" bpmnElement=\"Flow_0x0rsj4\">       <di:waypoint x=\"460\" y=\"200\" />       <di:waypoint x=\"660\" y=\"200\" />     </bpmndi:BPMNEdge>     <bpmndi:BPMNEdge id=\"Flow_1yz8x9c_di\" bpmnElement=\"Flow_1yz8x9c\">       <di:waypoint x=\"258\" y=\"200\" />       <di:waypoint x=\"360\" y=\"200\" />     </bpmndi:BPMNEdge>     <bpmndi:BPMNShape id=\"Event_07er7bi_di\" bpmnElement=\"Event_07er7bi\">       <dc:Bounds x=\"222\" y=\"182\" width=\"36\" height=\"36\" />       <bpmndi:BPMNLabel>         <dc:Bounds x=\"229\" y=\"225\" width=\"23\" height=\"14\" />       </bpmndi:BPMNLabel>     </bpmndi:BPMNShape>     <bpmndi:BPMNShape id=\"Activity_1cplga5_di\" bpmnElement=\"Activity_1cplga5\">       <dc:Bounds x=\"360\" y=\"160\" width=\"100\" height=\"80\" />     </bpmndi:BPMNShape>     <bpmndi:BPMNShape id=\"Activity_1ut4c1h_di\" bpmnElement=\"Activity_1ut4c1h\">       <dc:Bounds x=\"660\" y=\"160\" width=\"100\" height=\"80\" />     </bpmndi:BPMNShape>     <bpmndi:BPMNShape id=\"Event_1nxfq2q_di\" bpmnElement=\"Event_1nxfq2q\">       <dc:Bounds x=\"932\" y=\"182\" width=\"36\" height=\"36\" />       <bpmndi:BPMNLabel>         <dc:Bounds x=\"939\" y=\"225\" width=\"23\" height=\"14\" />       </bpmndi:BPMNLabel>     </bpmndi:BPMNShape>" +
                "</bpmndi:BPMNPlane> </bpmndi:BPMNDiagram></bpmn2:definitions>";

        Document dom = DocumentHelper.parseText(xml);
        //获取根节点root
        Element root = dom.getRootElement();
        List<Map> list = new ArrayList<>();
        for(Iterator i = root.elementIterator(); i.hasNext();){
            Element lists = (Element) i.next();
            if(lists.getName().equals("lists")) {
                Iterator iter = lists.elementIterator("item");
                while(iter.hasNext()){
                    Map  map = new HashMap();
                    Element a = (Element) iter.next();
                    String id = a.elementTextTrim("id");
                }
            }
        }
    }
}
