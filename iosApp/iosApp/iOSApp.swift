import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
     init() {
     HelperKt.initializeKoin()
     MainViewControllerKt.initialize()
     }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
