
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
		//하위폴더가 있는지 확인하고 있으면 하위목록을 만들어준다
		if(testService.getFolderTree(folderId)!=null) {
			model.addAttribute("isFolder",true);
			model.addAttribute("isLaszy",true);
			
			model.addAttribute("children",testService.getFolderTree(upperFldId));
		}
	}
}*/
