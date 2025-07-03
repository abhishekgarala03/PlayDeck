package app.abhishekgarala.playdeck.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import app.abhishekgarala.playdeck.ui.theme.PlayDeckTheme

@Composable
fun LoadingDialog(message: String? = null) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ),
    ) {
        Box(
            modifier = Modifier
                .background(
                    Color.White,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = message ?: "Please wait...",
                    style = MaterialTheme.typography.labelSmall

                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingDialogPreview() {
    PlayDeckTheme {
        LoadingDialog(message = "Please wait...")
    }
}
