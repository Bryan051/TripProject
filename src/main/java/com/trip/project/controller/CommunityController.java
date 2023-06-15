package com.trip.project.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trip.project.dto.CommunityDTO;
import com.trip.project.dto.ImageDTO;
import com.trip.project.dto.UploadFile;
import com.trip.project.file.FileStore;
import com.trip.project.service.CommunityService;

import io.opentelemetry.sdk.resources.Resource;

@Controller
@RequestMapping("/community")
public class CommunityController {

	private static final Logger logger = LoggerFactory.getLogger(CommunityController.class);

	@Autowired
	private CommunityService cService;

	// 커뮤니티 메인 페이지
	@RequestMapping("/communitymain")
	public String cummunityMain(Model model) {
		logger.info("COMMUNITY MAIN");
		model.addAttribute("list", cService.selectCommunity());

		return "communitymain";
	}

	// 커뮤니티 상세 페이지
	@RequestMapping("/communitydetail")
	public String communityDetail(Model model, int communityNumber) {
		logger.info("COMMUNITY DETAIL");
		model.addAttribute("dto", cService.selectOne(communityNumber));
//		model.addAttribute("image", cService.selectOneImg(communityNumber));

		ImageDTO imgDto = cService.selectOneImg(communityNumber);
		System.out.println(imgDto);
		model.addAttribute("image", imgDto);

		return "communitydetail";
	}

	// 커뮤니티 글쓰기 페이지
	@RequestMapping("/communitywriteform")
	public String communityWriteForm() {
		logger.info("COMMUNITY WRITE FORM");
		return "communitywriteform";
	}

	// 커뮤니티 글쓰기
	@RequestMapping("/communitywrite")
	public String communityWrite(CommunityDTO dto) throws IOException {
		logger.info("COMMUNITY WRITE");
		System.out.println("controller : " + dto.getAttachFile());
		// List<UploadFile> imagefile = FileStore.storeFiles(dto.getImageFiles());
		UploadFile file = FileStore.storeFile(dto.getAttachFile());

		// 게시글 insert
		int communityInsertRes = cService.insert(dto);
		int imageInsertRes = 0;
		
		//file을 선택했을때 
		if (file != null) {
			//방금 INSERT한 게시글의 번호를 SELECT한 다음 UploadFile 객체에 저장
			CommunityDTO tmp = cService.ComunityselectOne();
			file.setImageNumber(tmp.getCommunityNumber());
			//image insert
			imageInsertRes = cService.imageInsert(file);
			
			if (communityInsertRes > 0 &&  imageInsertRes> 0) {
				return "redirect:/community/communitymain";
			} else {
				return "redirect:/community/communitywriteform";
			}
		}else {
			if (communityInsertRes > 0 ) {
				return "redirect:/community/communitymain";
			} else {
				return "redirect:/community/communitywriteform";
			}
		}

		 
	}

	// 커뮤니티 수정 페이지
	@RequestMapping("/communityupdateform")
	public String communityUpdateForm(Model model, int communityNumber) {
		logger.info("UPDATEFORM COMMUNITY");
		model.addAttribute("dto", cService.selectOne(communityNumber));
		return "communityupdateform";
	}

	// 커뮤니티 수정
	@RequestMapping("/communityupdate")
	public String communityUpdate(CommunityDTO dto) {
		logger.info("UPDATE COMMUNITY");
		if (cService.update(dto) > 0) {
			return "redirect:/community/communitymain";
		} else {
			return "redirect:/community/communityupdateform";
		}
	}

	// 커뮤니티 삭제
	@RequestMapping("/communitydelete")
	public String communityDelete(int communityNumber) {
		logger.info("DELETE COMMUNITY");
		if (cService.delete(communityNumber) > 0) {
			return "redirect:/community/communitymain";
		} else {
			return "redirect:/community/communitydetail?communityNumber=" + communityNumber;
		}
	}

	@ResponseBody
	@RequestMapping("/image/{filename}")
	public UrlResource showImage(@PathVariable String filename) throws MalformedURLException {
		System.out.println("show : "+filename);
	    return new UrlResource("file:" + FileStore.getFullPath(filename));

	}
	@ResponseBody
	@PostMapping("/selectbox")
	public List<CommunityDTO> SelectBox(@RequestBody CommunityDTO dto) {
		List<CommunityDTO> data = null;
		
		if("all".equals(dto.getCommunityCategory())) {
			data = cService.selectCommunity();
		}else {
			data = cService.selectCommunityCategory(dto.getCommunityCategory());
		}				
		return data;
	}

}
