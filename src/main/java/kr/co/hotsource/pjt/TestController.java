
/*package kr.co.hotsource.ptj;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/test")
public class TestController {
	@Autowired
	private TestService testService;
	
	@RequestMapping("/getTree")
	public void getFolderTree(@RequestParam("folderId") String folderId, ModelMap model) {
		model.addAttribute("tutke",testService.getFolderDtl(upperFldId).getFIdNm());
		model.addAttribute("key",upperFldId)
		//���������� �ִ��� Ȯ���ϰ� ������ ��������� ������ش�
		if(testService.getFolderTree(folderId)!=null) {
			model.addAttribute("isFolder",true);
			model.addAttribute("isLaszy",true);
			
			model.addAttribute("children",testService.getFolderTree(upperFldId));
		}
	}
}*/
