package com.example.fanmon_be.domain.management.controller;

import com.example.fanmon_be.domain.management.service.GoodsService;
import com.example.fanmon_be.domain.management.service.ManagementService;
import com.example.fanmon_be.domain.shop.goods.entity.Goods;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServlet;
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

@Tag(name = "상품 관리 API",description = "상품관련 CRUD 작업 수행")
@RestController
@RequestMapping("/management/goods")
public class GoodsController {
    @Autowired
    private ManagementService service;
    @Autowired
    private GoodsService goodsService;

    @Operation(summary = "상품 리스트",description = "상품 리스트")
    @GetMapping
    public ResponseEntity<List<Goods>> findAll() {
        List<Goods> list = goodsService.getAllGoods();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Operation(summary = "상품 생성",description = "새로운 상품 생성")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Goods> createGoods(
            HttpServletRequest request,
            @Parameter(description = "생성할 상품 정보",required = true) @ModelAttribute Goods goods) {
        String path = request.getServletContext().getRealPath("/resources/goodsimg");
        System.out.println("real path : "+path);
        String fname = null;
        MultipartFile uploadfile = goods.getUploadfile();
        fname = uploadfile.getOriginalFilename();
        if(fname!=null && !fname.equals("")) {
            try{
                FileOutputStream fos = new FileOutputStream(new File(path+"/"+fname));
                FileCopyUtils.copy(uploadfile.getInputStream(), fos);
                fos.close();
                goods.setFname(fname);
            }catch (Exception e){
                System.out.println("파일 처리 예외! => "+e.getMessage());
            }
        }
        Goods createdGoods = goodsService.createGoods(goods);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGoods);
    }

    @Operation(summary = "상품 업데이트", description = "특정 상품 업데이트")
    @PutMapping("/{goodsuuid}")
    public ResponseEntity<Goods> updateGoods(
            @Parameter(description = "상품 uuid",required = true) @PathVariable UUID goodsuuid,
            @Parameter(description = "업데이트할 상품 정보",required = true) @RequestBody Goods goods){
        Goods updatedGoods = goodsService.updateGoods(goodsuuid, goods);
        return ResponseEntity.status(HttpStatus.OK).body(updatedGoods);
    }

    @Operation(summary = "상품 삭제", description = "특정 상품을 삭제합니다.")
    @DeleteMapping("/{goodsuuid}")
    public ResponseEntity<Goods> deleteGoods(
            @Parameter(description = "삭제할 상품 uuid", required = true) @PathVariable UUID goodsuuid){
        goodsService.deleteGoods(goodsuuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "상품 조회", description = "특정 상품을 조회합니다.")
    @GetMapping("/{goodsuuid}")
    public ResponseEntity<Goods> getGoods(
            @Parameter(description = "상품 uuid",required = true) @PathVariable UUID goodsuuid){
            Goods goods = goodsService.getGoodsById(goodsuuid);
            if(goods == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); //404 Not Found
            }
            return ResponseEntity.status(HttpStatus.OK).body(goods);
    }
}
