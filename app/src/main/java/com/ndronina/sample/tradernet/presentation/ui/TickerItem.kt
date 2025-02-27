package com.ndronina.sample.tradernet.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.ndronina.sample.tradernet.presentation.model.TickerUiModel

@Composable
fun TickerItem(uiModel: TickerUiModel) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        val (image, leftTitle, leftSubtitle, rightTitle, rightSubtitle, arrow) = createRefs()

        if (!uiModel.imageUrl.isNullOrEmpty()) {
            Image(
                painter = rememberAsyncImagePainter(model = uiModel.imageUrl),
                contentDescription = "Image",
                modifier = Modifier
                    .constrainAs(image) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    }
                    .wrapContentSize()
                    .sizeIn(
                        maxWidth = 20.dp,
                        maxHeight = 28.dp
                    )
            )
        }

        Text(
            text = uiModel.leftTitle,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .constrainAs(leftTitle) {
                    start.linkTo(image.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(rightSubtitle.top)
                }
                .fillMaxWidth(0.6f)
        )

        Text(
            text = uiModel.leftSubtitle,
            color = Color.Gray,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .constrainAs(leftSubtitle) {
                    start.linkTo(parent.start)
                    top.linkTo(leftTitle.bottom)
                }
                .fillMaxWidth(0.6f)
        )

        Box(
            modifier = Modifier
                .constrainAs(rightTitle) {
                    end.linkTo(arrow.start)
                    top.linkTo(leftTitle.top)
                }
                .background(
                    color = uiModel.rightTitleBackground,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 6.dp)
        ) {
            Text(
                text = uiModel.rightTitle,
                color = uiModel.rightTitleColor,
                fontSize = 20.sp
            )
        }

        Text(
            modifier = Modifier
                .constrainAs(rightSubtitle) {
                    end.linkTo(rightTitle.end)
                    top.linkTo(leftSubtitle.top)
                    bottom.linkTo(leftSubtitle.bottom)
                }
                .fillMaxWidth(0.4f)
            ,
            text = uiModel.rightSubtitle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Color.Black,
            fontSize = 12.sp,
            textAlign = TextAlign.Right
        )

        Icon(
            imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
            contentDescription = "arrow right",
            modifier = Modifier
                .constrainAs(arrow) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .size(20.dp),
            tint = Color.LightGray
        )
    }
}

