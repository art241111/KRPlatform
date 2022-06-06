package view.ActionText

data class ActionText(
    val name: String,
    val sections: Map<String, () -> Unit> = mapOf(),
    val onClick: () -> Unit
)