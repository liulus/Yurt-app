package com.github.liulus.yurt.system.controller

import com.github.liulus.yurt.convention.data.Page
import com.github.liulus.yurt.system.model.dto.DictDTO
import com.github.liulus.yurt.system.service.DictionaryService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.Resource
import javax.validation.Valid

/**
 *
 * @author liulu
 * @version V1.0
 * @since 2021/3/2
 */
@RestController
@RequestMapping("/api/dict")
open class DictionaryController {

    val log = LoggerFactory.getLogger(DictionaryController::class.java)

    @Resource
    private lateinit var dictionaryService: DictionaryService

    @GetMapping("/list")
    fun getDictList(@Valid query: DictDTO.Query): Page<DictDTO.Detail> {
        log.info("query value")
        return dictionaryService.findPageList(query)
    }

    @PostMapping
    fun add(@RequestBody @Valid add: DictDTO.Detail): Long {
        return dictionaryService.insert(add)
    }

}