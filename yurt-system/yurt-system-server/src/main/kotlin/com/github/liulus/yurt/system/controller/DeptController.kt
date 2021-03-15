package com.github.liulus.yurt.system.controller

import com.github.liulus.yurt.system.model.dto.DeptDTO
import com.github.liulus.yurt.system.service.DeptService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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
 * @since 2021/3/11
 */
@RestController
@RequestMapping("/api/dept")
open class DeptController {

    @Resource
    private lateinit var deptService: DeptService

    @GetMapping("/tree")
    fun deptTree(): List<DeptDTO.Detail> {
        return deptService.buildDeptTree()
    }

    @PostMapping
    fun add(@RequestBody @Valid add: DeptDTO.Detail): Long {
        return deptService.insert(add)
    }

    @PutMapping
    fun update(@RequestBody @Valid update: DeptDTO.Detail): Int {
        return deptService.update(update)
    }

    @PostMapping("/change/status/{id}")
    fun changeStatus(@PathVariable id: Long) {
        deptService.changeStatus(id)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        deptService.delete(id)
    }

}