package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class GetThemeUseCase @Inject constructor(
    private val repository: HoneyMartRepository,
) {
     operator  fun invoke():Boolean {
      return repository.getThemeState()
    }
}