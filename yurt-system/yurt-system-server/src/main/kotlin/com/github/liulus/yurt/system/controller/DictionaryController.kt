package com.github.liulus.yurt.system.controller

import com.github.liulus.yurt.convention.data.Page
import com.github.liulus.yurt.system.model.dto.DictDTO
import com.github.liulus.yurt.system.service.DictionaryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
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

    @Resource
    private lateinit var dictionaryService: DictionaryService

    @GetMapping("/list")
    fun getDictList(@Valid query: DictDTO.Query): Page<DictDTO.Detail> {
        return dictionaryService.findPageList(query)
    }

    @PostMapping
    fun add(@RequestBody @Valid add: DictDTO.Detail): Long {
        return dictionaryService.insert(add)
    }

    @PutMapping
    fun update(@RequestBody @Valid update: DictDTO.Detail): Int {
        return dictionaryService.update(update)
    }

}