package com.anafthdev.composeresearch.uicomponent

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import kotlinx.coroutines.launch

@Preview
@Composable
private fun FlippableCardPreview() {

    val state = rememberFlippableCardState()
    val coroutineScope = rememberCoroutineScope()

    FlippableCard(
        state = state,
        onClick = {
            coroutineScope.launch {
                state.flip()
            }
        },
        front = {
            Text(
                text = "Front",
                modifier = Modifier
                    .padding(24.dp)
            )
        },
        back = {
            Text(
                text = "Back",
                modifier = Modifier
                    .padding(24.dp)
            )
        }
    )
}

/**
 * Flippable card
 *
 * @param onClick called when this card is clicked and clickable, null if card non clickable
 * @param modifier the [Modifier] to be applied to this card
 * @param enabled controls the enabled state of this card. When `false`, this component will not
 * respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param shape defines the shape of this card's container, border (when [border] is not null), and
 * shadow (when using [elevation])
 * @param colors [CardColors] that will be used to resolve the color(s) used for this card in
 * different states. See [CardDefaults.cardColors].
 * @param elevation [CardElevation] used to resolve the elevation for this card in different states.
 * This controls the size of the shadow below the card. Additionally, when the container color is
 * [ColorScheme.surface], this controls the amount of primary color applied as an overlay. See also:
 * [Surface].
 * @param border the border to draw around the container of this card
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this card. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this card in different states.
 *
 */
@Composable
fun FlippableCard(
    front: @Composable ColumnScope.() -> Unit,
    back: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    state: FlippableCardState = rememberFlippableCardState(),
    enabled: Boolean = true,
    shape: Shape = CardDefaults.shape,
    colors: CardColors = CardDefaults.cardColors(),
    elevation: CardElevation = CardDefaults.cardElevation(),
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: (() -> Unit)? = null,
) {

    val graphicsLayerModifier = Modifier
        .graphicsLayer {
            if (state.cardSide.isFront) {
                rotationZ = if (FlipRotation.Z in state.frontFlipRotation) state.currentAngle else 0f
                rotationX = if (FlipRotation.X in state.frontFlipRotation) state.currentAngle else 0f
                rotationY = if (FlipRotation.Y in state.frontFlipRotation) state.currentAngle else 0f
            } else {
                rotationZ = if (FlipRotation.Z in state.backFlipRotation) state.currentAngle else 0f
                rotationX = if (FlipRotation.X in state.backFlipRotation) state.currentAngle else 0f
                rotationY = if (FlipRotation.Y in state.backFlipRotation) state.currentAngle else 0f
            }
        }

    FlipCard(
        onClick = onClick,
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        interactionSource = interactionSource,
        modifier = modifier
            .then(graphicsLayerModifier)
    ) {
        Column(
            content = if (state.cardSide.isFront && state.flipFraction <= 0.5f) front else back,
            modifier = graphicsLayerModifier
        )
    }
}

@Composable
fun rememberFlippableCardState(
    initialCardSide: CardSide = CardSide.Front,
    frontAngle: Float = 0f,
    backAngle: Float = 180f,
    frontFlipRotation: Set<FlipRotation> = setOf(FlipRotation.Y),
    backFlipRotation: Set<FlipRotation> = setOf(FlipRotation.Y),
): FlippableCardState {
    return rememberSaveable(
        inputs = arrayOf(frontFlipRotation, backFlipRotation),
        saver = FlippableCardState.Saver()
    ) {
        FlippableCardState(
            initialCardSide = initialCardSide,
            frontAngle = frontAngle,
            backAngle = backAngle,
            frontFlipRotation = frontFlipRotation,
            backFlipRotation = backFlipRotation,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FlipCard(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = CardDefaults.shape,
    colors: CardColors = CardDefaults.cardColors(),
    elevation: CardElevation = CardDefaults.cardElevation(),
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: (() -> Unit)?,
    content: @Composable ColumnScope.() -> Unit,
) {
    if (onClick != null) {
        Card(
            modifier = modifier,
            onClick = onClick,
            content = content,
            enabled = enabled,
            shape = shape,
            colors = colors,
            elevation = elevation,
            border = border,
            interactionSource = interactionSource,
        )
    } else {
        Card(
            modifier = modifier,
            content = content,
            shape = shape,
            colors = colors,
            elevation = elevation,
            border = border,
        )
    }
}

@Stable
class FlippableCardState(
    val frontAngle: Float,
    val backAngle: Float,
    frontFlipRotation: Set<FlipRotation>,
    backFlipRotation: Set<FlipRotation>,
    initialCardSide: CardSide
) {

    private val animatable = Animatable(
        if (initialCardSide.isFront) frontAngle else backAngle
    )

    var cardSide by mutableStateOf(initialCardSide)
    val frontFlipRotation by mutableStateOf(frontFlipRotation)
    val backFlipRotation by mutableStateOf(backFlipRotation)

    /**
     * Indicates whether the animation is running.
     */
    val isRunning: Boolean
        get() = animatable.isRunning

    /**
     * The target of the current animation. If the animation finishes un-interrupted, it will reach this target value.
     */
    val targetValue: Float
        get() = animatable.targetValue

    /**
     * Current angle of the animation.
     */
    val currentAngle: Float
        get() = animatable.value

    /**
     * 0f if front, 1f if back
     */
    val flipFraction: Float
        get() = (animatable.value - frontAngle) / backAngle - frontAngle

    /**
     * Flip the card.
     *
     * If current side is [CardSide.Front], the card will be turned backwards,
     * otherwise, the card will be turned forward.
     *
     * The animation will use the provided [animationSpec] to animate the value towards the [targetValue].
     * When no [animationSpec] is specified, a spring will be used.
     */
    suspend fun flip(
        animationSpec: AnimationSpec<Float> = tween(256)
    ) {
        cardSide = cardSide.next()
        animatable.animateTo(
            targetValue = if (cardSide.isFront) frontAngle else backAngle,
            animationSpec = animationSpec,
        )
    }

    companion object {

        private const val INITIAL_CARD_SIDE = "initial_card_side"
        private const val FRONT_ANGLE = "front_angle"
        private const val BACK_ANGLE = "back_angle"
        private const val FRONT_FLIP_ROTATION = "front_flip_rotation"
        private const val BACK_FLIP_ROTATION = "back_flip_rotation"

        fun Saver() = mapSaver(
            save = { state ->
                mapOf(
                    INITIAL_CARD_SIDE to state.cardSide.ordinal,
                    FRONT_ANGLE to state.frontAngle,
                    BACK_ANGLE to state.backAngle,
                    FRONT_FLIP_ROTATION to Gson().toJson(state.frontFlipRotation.map { it.ordinal }),
                    BACK_FLIP_ROTATION to Gson().toJson(state.backFlipRotation.map { it.ordinal }),
                )
            },
            restore = { map ->
                FlippableCardState(
                    initialCardSide = CardSide.entries[map[INITIAL_CARD_SIDE].toString().toIntOrNull() ?: 0],
                    frontAngle = map[FRONT_ANGLE]?.toString()?.toFloatOrNull() ?: 0f,
                    backAngle = map[BACK_ANGLE]?.toString()?.toFloatOrNull() ?: 180f,
                    frontFlipRotation = map[FRONT_FLIP_ROTATION]?.let {
                        Gson().fromJson(it.toString(), Array<Int>::class.java).map { FlipRotation.entries[it] }.toSet()
                    } ?: setOf(FlipRotation.Y),
                    backFlipRotation = map[BACK_FLIP_ROTATION]?.let {
                        Gson().fromJson(it.toString(), Array<Int>::class.java).map { FlipRotation.entries[it] }.toSet()
                    } ?: setOf(FlipRotation.Y)
                )
            }
        )
    }
}

enum class CardSide {
    Front,
    Back;

    val isFront: Boolean
        get() = this == Front

    val isBack: Boolean
        get() = this == Back

    fun next(): CardSide = if (this == Front) Back else Front
}

/**
 * Graphics layer rotation
 */
enum class FlipRotation {
    X, Y, Z
}
