package com.example.fanmon_be.domain.management.controller;

import com.example.fanmon_be.domain.artist.entity.Team;
import com.example.fanmon_be.domain.management.entity.Management;
import com.example.fanmon_be.domain.management.service.GoodsService;
import com.example.fanmon_be.domain.management.service.ManagementService;
import com.example.fanmon_be.domain.shop.goods.entity.Goods;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;

@Tag(name = "상품 관리 API", description = "상품관련 CRUD 작업 수행")
@RestController
@RequestMapping("/management/goods")
public class GoodsController {
    @Autowired
    private ManagementService service;
    @Autowired
    private GoodsService goodsService;

    //전체 굿즈 리스트
    @Operation(summary = "상품 리스트", description = "상품 리스트")
    @GetMapping
    public ResponseEntity<List<Goods>> findAll() {
        List<Goods> list = goodsService.getAllGoods();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //상품 등록 CREATE
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Goods> createGoods(
            HttpServletRequest request,
            @ModelAttribute Goods goods,
            @RequestParam("managementuuid") UUID managementuuid,
            @RequestParam("teamuuid") UUID teamuuid) {
        //프론트에서 넘어온 uuid 정보들 포함시키도록
        goods.setManagement(new Management(managementuuid));
        goods.setTeam(new Team(teamuuid));

        String path = request.getServletContext().getRealPath("/resources/goodsimg");
        System.out.println("real path : " + path);
        String fname = null;
        MultipartFile uploadfile = goods.getUploadfile();
        fname = uploadfile.getOriginalFilename();
        if (fname != null && !fname.equals("")) {
            try {
                FileOutputStream fos = new FileOutputStream(new File(path + "/" + fname));
                FileCopyUtils.copy(uploadfile.getInputStream(), fos);
                fos.close();
                goods.setFname(fname);
            } catch (Exception e) {
                System.out.println("파일 처리 예외! => " + e.getMessage());
            }
        }
        Goods createdGoods = goodsService.createGoods(goods);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGoods);
    }

    //굿즈 수정 UPDATE
    @PutMapping("/{goodsuuid}")
    public ResponseEntity<Goods> updateGoods(
            @PathVariable UUID goodsuuid,
            HttpServletRequest request,
            @ModelAttribute Goods goods,
            @RequestParam("managementuuid") UUID managementuuid,
            @RequestParam("teamuuid") UUID teamuuid) {
        System.out.println("굿즈 수정하는 managementuuid : " + managementuuid + "\n팀 teamuuid : " + teamuuid);
        goods.setManagement(new Management(managementuuid));
        goods.setTeam(new Team(teamuuid));
        //업뎃된 파일 있으면 그걸로 fname set해서 db에 저장
        String oldFname = goods.getFname();
        MultipartFile uploadfile = goods.getUploadfile();
        String fname = null;
        if (uploadfile != null) {
            fname = uploadfile.getOriginalFilename();
            goods.setFname(fname);
        }
        Goods updatedGoods = goodsService.updateGoods(goodsuuid, goods);

        //파일 관리
        if (updatedGoods != null && fname != null && !fname.equals("")) {
            String path = request.getServletContext().getRealPath("/resources/goodsimg");
            //원래 파일 삭제
            File file = new File(path + "/" + oldFname);
            file.delete();
            try {
                //업뎃된 파일 저장
                byte[] data = uploadfile.getBytes();
                FileOutputStream fos = new FileOutputStream(path + "/" + fname);
                fos.write(data);
                fos.close();
            } catch (Exception e) {
                System.out.println("파일 업로드 예외 : " + e.getMessage());
            }
        }
        if (updatedGoods == null) {
            System.out.println("파일 업로드 실패");
        }
        return ResponseEntity.status(HttpStatus.OK).body(updatedGoods);
    }

    //굿즈 삭제 DELETE
    @DeleteMapping("/{goodsuuid}")
    public ResponseEntity<Goods> deleteGoods(@PathVariable UUID goodsuuid) {
        System.out.println("삭제할 goodsuuid : " + goodsuuid);
        goodsService.deleteGoods(goodsuuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //굿즈 디테일
    @GetMapping("/{goodsuuid}")
    public ResponseEntity<Goods> getGoods(
            @PathVariable UUID goodsuuid) {
        Goods goods = goodsService.getGoodsById(goodsuuid);
        if (goods == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); //404 Not Found
        }
        return ResponseEntity.status(HttpStatus.OK).body(goods);
    }

    //팀에 따른 굿즈 리스트
    @GetMapping("/team/{teamuuid}")
    public ResponseEntity<List<Goods>> getGoodsByTeamuuid(@PathVariable UUID teamuuid) {
        List<Goods> list = goodsService.getGoodsByTeamuuid(teamuuid);
        if (list == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        System.out.println("team's Goods List : " + list);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //management 별 굿즈 COUNT
    @GetMapping("/count/{managementuuid}")
    public ResponseEntity<Long> countByManagementuuid(@PathVariable UUID managementuuid) {
        Long count = goodsService.countByManagementuuid(managementuuid);
        if (count == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(count);
    }
}
