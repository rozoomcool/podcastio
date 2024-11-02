package com.govzcode.podcastio.service

import com.govzcode.podcastio.entity.User
import com.govzcode.podcastio.exception.EntityAlreadyExistsException
import com.govzcode.podcastio.model.CustomUserDetails
import com.govzcode.podcastio.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserService(
    private val repository: UserRepository
) : UserDetailsService {

    /**
     * Сохранение пользователя
     *
     * @return сохраненный пользователь
     */
    private fun save(user: User): User {
        return repository.save(user)
    }

    override fun loadUserByUsername(username: String): UserDetails {
        return CustomUserDetails(
            repository.findByUsername(username) ?: throw UsernameNotFoundException("User not found")
        )
    }

    /**
     * Создание пользователя
     *
     * @return созданный пользователь
     */
    fun create(user: User): User {
        if (repository.existsByUsername(user.username)) {
            throw EntityAlreadyExistsException("Пользователь с таким именем уже существует")
        }
        return save(user)
    }

    /**
     * Получение пользователя по имени пользователя
     *
     * @return пользователь
     */
    fun getByUsername(username: String?): User {
        return repository.findByUsername(username!!) ?: throw UsernameNotFoundException("Пользователь не найден")
    }

    /**
     * Получение текущего пользователя
     *
     * @return текущий пользователь
     */
    fun getCurrentUser(): User {
        // Получение имени пользователя из контекста Spring Security
        val username = SecurityContextHolder.getContext().authentication.name
        return getByUsername(username)
    }
}