package com.embabel.example.horoscope

import org.springframework.stereotype.Service

/**
 * Service for retrieving horoscope information.
 * This is a mock implementation for demonstration purposes.
 */
@Service
class HoroscopeService {
    
    /**
     * Retrieves a daily horoscope for the given star sign.
     * 
     * @param sign The star sign (e.g., "Aries", "Taurus", etc.)
     * @return A horoscope message for the given sign
     */
    fun dailyHoroscope(sign: String): String {
        val horoscopes = mapOf(
            "aries" to "Today is a day of bold action for Aries. Your natural leadership qualities will shine, and you may find yourself taking charge of situations that need direction. Trust your instincts and don't hesitate to make decisions.",
            "taurus" to "Taurus, your practical nature serves you well today. Focus on building solid foundations and completing tasks that have been pending. Your patience and determination will be rewarded with tangible results.",
            "gemini" to "Gemini, your curiosity is heightened today. You'll find yourself drawn to new ideas and conversations that expand your horizons. Communication flows easily, making this a great day for networking and learning.",
            "cancer" to "Cancer, your emotional intelligence is your superpower today. You'll be particularly attuned to the feelings of others, making you an excellent mediator. Trust your intuition in personal matters.",
            "leo" to "Leo, your charisma is off the charts today! People are naturally drawn to your warmth and enthusiasm. This is a perfect day to showcase your talents and take center stage in creative projects.",
            "virgo" to "Virgo, your attention to detail will be especially valuable today. You may notice things others miss, leading to important insights. Focus on organization and efficiency in your daily tasks.",
            "libra" to "Libra, your sense of balance and fairness guides you today. You'll excel in situations that require diplomacy and compromise. Trust your ability to see multiple perspectives and find harmonious solutions.",
            "scorpio" to "Scorpio, your intensity and focus are heightened today. You may uncover hidden truths or gain deeper insights into situations. Your determination will help you overcome any obstacles.",
            "sagittarius" to "Sagittarius, your adventurous spirit calls you to explore new territories today. Whether physical or intellectual, embrace opportunities for expansion and growth. Your optimism is contagious.",
            "capricorn" to "Capricorn, your disciplined approach pays off today. You'll make steady progress toward your long-term goals. Your reliability and hard work are recognized by those around you.",
            "aquarius" to "Aquarius, your innovative thinking leads to breakthroughs today. You may come up with unique solutions to problems or see connections others miss. Embrace your individuality and original ideas.",
            "pisces" to "Pisces, your compassion and creativity are flowing freely today. You'll be particularly sensitive to the emotions of others and may find yourself inspired artistically. Trust your dreams and intuition."
        )
        
        return horoscopes[sign.lowercase()] ?: "Today brings new opportunities for growth and discovery. Trust in your journey and embrace the possibilities that come your way."
    }
} 