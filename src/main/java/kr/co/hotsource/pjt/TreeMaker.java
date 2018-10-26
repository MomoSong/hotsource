package kr.co.hotsource.pjt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TreeMaker {

    /**
     * �θ��ڵ带 �̿��Ͽ� ������ Ʈ�� ����.     
     */
    public String makeTreeByHierarchy(List<?> listview) {
        List<TreeVO> rootlist = new ArrayList<TreeVO>();
        
        for (Integer i = 0; i < listview.size(); i++) {            
            TreeVO mtDO = (TreeVO)listview.get(i);            
            
            if (mtDO.getParent() == null) {
                rootlist.add(mtDO);
                continue;
            }    
             for (Integer j = 0; j < listview.size(); j++) {
                 TreeVO ptDO = (TreeVO) listview.get(j);
                 if (mtDO.getParent().equals(ptDO.getKey())) {
                     if (ptDO.getChildren() == null) {
                         ptDO.setChildren(new ArrayList<Object>() );
                     }
                     List<TreeVO> list = ptDO.getChildren();
                     list.add(mtDO);
                     ptDO.setIsFolder(true);
                     break;
                 }
             }     
         }

        ObjectMapper mapper = new ObjectMapper();
        String str = "";
        try {
            str = mapper.writeValueAsString(rootlist);
        } catch (IOException ex) {
            System.out.println("TreeMaker ���� : " + ex);
        }
        return str;
    }
}
