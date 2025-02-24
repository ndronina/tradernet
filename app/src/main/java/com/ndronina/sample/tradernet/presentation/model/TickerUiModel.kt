package com.ndronina.sample.tradernet.presentation.model

import androidx.compose.ui.graphics.Color

/**
 * Модель presentation слоя для тикера
 *
 * @property imageUrl Ссылка на изображение логотипа компании
 * @property contentDescription Описание картинки
 * @property leftTitle Заголовок слева карточки
 * @property leftSubtitle Подзаголовок слева карточки
 * @property rightTitle Заголовок справа карточки
 * @property rightSubtitle Подзаголовок справа карточки
 * @property rightTitleColor Цвет заголовка справа
 * @property rightTitleBackground Цвет подстветки заголовка справа
 */
data class TickerUiModel(
    val imageUrl: String?,
    val contentDescription: String,
    val leftTitle: String,
    val leftSubtitle: String,
    val rightTitle: String,
    val rightSubtitle: String,
    val rightTitleColor: Color,
    val rightTitleBackground: Color
)