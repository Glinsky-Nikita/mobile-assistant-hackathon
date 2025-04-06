package com.example.mobileassistant.presentation.compnents



import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.mobileassistant.ui.theme.GradPrimary
import com.example.mobileassistant.ui.theme.GrayPrimary
import com.example.mobileassistant.ui.theme.Purple80

@Composable
fun CircleIconButton(
    onClick: () -> Unit,
    icon: Any,
    contentDescription: String = "",
    iconSize: Dp = 38.dp,
    backgroundColor: Color,
    enabled: Boolean = true,
    modifier: Modifier,
) {


    Box(
        modifier = modifier
            .background(color = backgroundColor, shape = CircleShape)
            .clip(CircleShape)
            .padding(2.dp)
            .clickable(
                onClick = onClick,
                enabled = enabled,
                role = Role.Button
            ),
        contentAlignment = Alignment.Center
    ) {
        when (icon) {
            is ImageVector -> Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = Color.White,
                modifier = Modifier
                    .background(Color.Transparent)
                    .padding(10.dp)
                    .aspectRatio(1f)
            )
            is Painter -> Icon(
                painter = icon,
                contentDescription = contentDescription,
                tint = Color.White,
                modifier = Modifier
                    .size(iconSize)
                    .aspectRatio(1f)
            )
            else -> throw IllegalArgumentException("Icon must be ImageVector or Painter")
        }
    }
}

@Composable
fun CircleIconButton(
    onClick: () -> Unit,
    icon: Any,
    contentDescription: String = "",
    iconSize: Dp = 38.dp,
    backgroundColor: Brush,
    enabled: Boolean = true,
    modifier: Modifier,
) {
    Box(
        modifier = modifier
            .background(brush = backgroundColor, shape = CircleShape)
            .clip(CircleShape)
            .padding(2.dp)
            .clickable(
                onClick = onClick,
                enabled = enabled,
                role = Role.Button
            ),
        contentAlignment = Alignment.Center
    ) {
        when (icon) {
            is ImageVector -> Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = Color.White,
                modifier = Modifier
                    .background(Color.Transparent)
                    .padding(10.dp)
                    .aspectRatio(1f)
            )
            is Painter -> Icon(
                painter = icon,
                contentDescription = contentDescription,
                tint = Color.White,
                modifier = Modifier
                    .size(iconSize)
                    .aspectRatio(1f)
            )
            else -> throw IllegalArgumentException("Icon must be ImageVector or Painter")
        }
    }
}

@Preview
@Composable
fun ButtonPreview(){
    CircleIconButton(
        onClick = {  },
        icon = Icons.Default.Favorite,
        contentDescription = "Избранное",
        iconSize = 32.dp,
        backgroundColor = GradPrimary,
        modifier = Modifier
            .size(48.dp)
    )
}