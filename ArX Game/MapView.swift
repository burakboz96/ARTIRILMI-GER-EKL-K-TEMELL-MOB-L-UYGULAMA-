import SwiftUI

struct MapView: View {
    var avatar: Avatar
    var avatarName: String

    @State private var selectedBackground = "Gündüz"
    private let backgrounds = ["Gündüz", "Gece", "Diğer"]
    
    @State private var selectedGameIndex = 0
    private let timer = Timer.publish(every: 3, on: .main, in: .common).autoconnect()

    var body: some View {
        NavigationStack {
            ZStack {
                Image(selectedBackground == "Gündüz" ? "day_background" :
                      selectedBackground == "Gece" ? "night_background" : "other_background")
                    .resizable()
                    .scaledToFill()
                    .ignoresSafeArea()

                VStack(spacing: 25) {
                    Spacer().frame(height: 40)

                    // Arka Plan Seçimi
                    HStack(spacing: 15) {
                        ForEach(backgrounds, id: \.self) { background in
                            Button(action: {
                                selectedBackground = background
                            }) {
                                Text(background)
                                    .fontWeight(.bold)
                                    .padding(.vertical, 8)
                                    .padding(.horizontal, 16)
                                    .background(selectedBackground == background ? Color.orange : Color.white.opacity(0.2))
                                    .foregroundColor(.white)
                                    .cornerRadius(10)
                                    .shadow(radius: 4)
                            }
                        }
                    }
                    .padding(.horizontal, 20)

                    Text("Oyununu Seç")
                        .font(.system(size: 26, weight: .bold))
                        .foregroundColor(.white)
                        .shadow(radius: 5)

                    GeometryReader { proxy in
                        let width = proxy.size.width
                        let cardWidth: CGFloat = 240
                        let spacing: CGFloat = 20

                        ScrollView(.horizontal, showsIndicators: false) {
                            HStack(spacing: spacing) {
                                ForEach(0..<gameIcons.count, id: \.self) { index in
                                    GeometryReader { geo in
                                        let x = geo.frame(in: .global).midX
                                        let diff = abs(x - width / 2)
                                        let scale = max(1.2 - (diff / width), 0.8)

                                        GameCard(
                                            icon: gameIcons[index],
                                            title: gameTitles[index],
                                            index: index,
                                            selectedGameIndex: $selectedGameIndex
                                        )
                                        .scaleEffect(scale)
                                        .animation(.easeInOut(duration: 0.4), value: scale)
                                    }
                                    .frame(width: cardWidth, height: 320)
                                }
                            }
                            .padding(.horizontal, (width - cardWidth) / 2)
                        }
                        .frame(height: 340)
                        .onReceive(timer) { _ in
                            withAnimation {
                                selectedGameIndex = (selectedGameIndex + 1) % gameIcons.count
                            }
                        }
                    }

                    Spacer()
                }
                .padding(.top, 10)
            }
            .navigationBarHidden(true)
        }
    }
}

struct GameCard: View {
    let icon: String
    let title: String
    let index: Int
    @Binding var selectedGameIndex: Int

    var body: some View {
        VStack(spacing: 16) {
            ZStack {
                Circle()
                    .fill(LinearGradient(colors: [Color.purple.opacity(0.9), Color.blue.opacity(0.9)], startPoint: .topLeading, endPoint: .bottomTrailing))
                    .frame(width: 130, height: 130)
                    .shadow(radius: 6)

                Image(icon)
                    .resizable()
                    .scaledToFit()
                    .frame(width: 75, height: 75)
            }

            Text(title)
                .foregroundColor(.white)
                .font(.headline)
                .shadow(radius: 2)

            NavigationLink(destination: destinationView(for: title)) {
                Text("Oyna")
                    .font(.subheadline)
                    .fontWeight(.bold)
                    .foregroundColor(.white)
                    .padding(.vertical, 10)
                    .padding(.horizontal, 20)
                    .background(Color.orange)
                    .cornerRadius(16)
                    .shadow(radius: 5)
            }
        }
        .padding()
        .background(Color.black.opacity(0.25))
        .cornerRadius(20)
        .shadow(radius: 8)
    }

    func destinationView(for gameTitle: String) -> some View {
        switch gameTitle {
        case "Fly Bird":
            return AnyView(FlyBirdGameView())
        case "Snake":
            return AnyView(SnakeGameView())
        case "Balloon":
            return AnyView(BalloonGameView())
        case "Ülke Bilmece":
            return AnyView(CountryQuizGameView1())
        case "Hazine Avı":
            return AnyView(TreasureHuntGameView())
        default:
            return AnyView(Text("Game not found"))
        }
    }
}

let gameIcons = ["flybird", "snakegame", "balloonpop", "countryquiz", "treasurehunt"]
let gameTitles = ["Fly Bird", "Snake", "Balloon", "Ülke Bilmece", "Hazine Avı"]

