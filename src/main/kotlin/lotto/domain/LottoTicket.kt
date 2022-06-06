package lotto.domain

import lotto.domain.enum.Priority

class LottoTicket(private val lottoNumbers: Set<LottoNumber>) {
    val numbers: Set<Int> = lottoNumbers.map { it.number }.toSet()

    init {
        require(lottoNumbers.size == LOTTO_NUMBERS_LENGTH) { "로또 번호는 6자리여야 합니다." }
    }

    fun priority(winningTicket: WinningTicket): Priority {
        val matchCount = calculateMatch(winningTicket)
        return if (isBonusTicket(matchCount, winningTicket.bonusNumber)) Priority.SECOND else Priority.find(matchCount)
    }

    private fun calculateMatch(winningTicket: WinningTicket): Int {
        return numbers.filter { winningTicket.numbers.contains(it) }.size
    }

    private fun isBonusTicket(matchCount: Int, bonusNumber: Int): Boolean {
        return numbers.contains(bonusNumber) && matchCount == BONUS_CANDIDATE_COUNT
    }

    companion object {
        private const val BONUS_CANDIDATE_COUNT = 4
        private const val LOTTO_NUMBERS_LENGTH = 6
    }
}