package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class SaveThemeUseCase @Inject constructor(
    private val repository: HoneyMartRepository,
) {
    suspend operator fun invoke(isDark: Boolean) {
        repository.saveThemeState(isDark)
    }
}