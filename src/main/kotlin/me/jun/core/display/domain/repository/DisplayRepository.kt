package me.jun.core.display.domain.repository

import org.springframework.data.domain.Page

interface DisplayRepository<T> {

    fun retrieveDisplay(page: Int, size: Int): Page<T>
}