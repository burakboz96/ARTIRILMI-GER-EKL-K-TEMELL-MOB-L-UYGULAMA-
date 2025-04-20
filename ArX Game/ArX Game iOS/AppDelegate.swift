import UIKit
import SwiftUI

@main
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?

    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        
        // SwiftUI görünümünü oluştur
        let splashScreen = SplashScreen()

        // SwiftUI görünümünü UIKit'e bağla
        let window = UIWindow(frame: UIScreen.main.bounds)
        window.rootViewController = UIHostingController(rootView: splashScreen)
        self.window = window
        window.makeKeyAndVisible()

        return true
    }

    func applicationWillResignActive(_ application: UIApplication) {
        // Uygulama aktif durumdan pasife geçtiğinde çağrılır
    }

    func applicationDidEnterBackground(_ application: UIApplication) {
        // Uygulama arka plana geçtiğinde çağrılır
    }

    func applicationWillEnterForeground(_ application: UIApplication) {
        // Uygulama arka plandan dönerken çağrılır
    }

    func applicationDidBecomeActive(_ application: UIApplication) {
        // Uygulama yeniden aktif olduğunda çağrılır
    }

    func applicationWillTerminate(_ application: UIApplication) {
        // Uygulama tamamen kapanmadan önce çağrılır
    }
}

