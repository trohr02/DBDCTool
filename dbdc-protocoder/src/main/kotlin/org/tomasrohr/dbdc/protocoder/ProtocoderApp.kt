package org.tomasrohr.dbdc.protocoder

import javafx.application.Application
import tornadofx.App

class CustomerApp : App(ProtocoderForm::class)

fun main(args: Array<String>) {
    Application.launch(CustomerApp::class.java, *args)
}