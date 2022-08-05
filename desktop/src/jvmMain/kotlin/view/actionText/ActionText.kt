package view.actionText

data class ActionText(
    val name: String,
    val sections: Map<String, () -> Unit> = mapOf(),
    val onClick: () -> Unit
)