package com.example.coffick.components

//// Register a callback for the Activity Result
//val photoPicker = rememberLauncherForActivityResult(PhotoPicker()) { uris ->
//    // uris contain the list of selected images & video
//    println(uris)
//}
//
//Column {
//    Button(onClick = {
//        // Launch the picker with only one image selectable
//        photoPicker.launch(PhotoPicker.Args(PhotoPicker.Type.IMAGES_ONLY, 1))
//    }) {
//        Text("Select 1 image max")
//    }
//
//    Button(onClick = {
//        // Launch the picker with 15 video selectable
//        photoPicker.launch(PhotoPicker.Args(PhotoPicker.Type.VIDEO_ONLY, 15))
//    }) {
//        Text("Select 15 video max")
//    }
//
//    Button(onClick = {
//        // Launch the picker with 5 max images & video selectable
//        photoPicker.launch(PhotoPicker.Args(PhotoPicker.Type.IMAGES_AND_VIDEO, 5))
//    }) {
//        Text("Select 5 images & video max")
//    }
//}

// https://google.github.io/modernstorage/photopicker/