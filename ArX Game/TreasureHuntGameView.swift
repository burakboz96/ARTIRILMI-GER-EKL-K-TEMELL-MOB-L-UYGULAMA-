import SwiftUI

struct TreasureHuntGameView: View {
    @State private var treasureLocation = Int.random(in: 0..<36)
    @State private var revealedCells = Array(repeating: false, count: 36)
    @State private var attempts = 0
    @State private var treasureFound = false
    @State private var showRestart = false
    
    let gridSize = 6
    
    var body: some View {
        VStack(spacing: 20) {
            Text("🏴‍☠️ Hazine Avı")
                .font(.largeTitle)
                .fontWeight(.heavy)
                .foregroundColor(.brown)
                .padding(.top, 40)
            
            Text("Hazineyi bulmak için karelere tıkla!")
                .font(.headline)
                .foregroundColor(.black)
            
            LazyVGrid(columns: Array(repeating: GridItem(.flexible()), count: gridSize), spacing: 12) {
                ForEach(0..<gridSize * gridSize, id: \.self) { index in
                    Button(action: {
                        if !revealedCells[index] && !treasureFound {
                            revealedCells[index] = true
                            attempts += 1
                            if index == treasureLocation {
                                treasureFound = true
                                showRestart = true
                            }
                        }
                    }) {
                        ZStack {
                            RoundedRectangle(cornerRadius: 8)
                                .fill(revealedCells[index] ? (index == treasureLocation ? Color.yellow : Color.gray.opacity(0.5)) : Color.blue)
                                .frame(height: 40)
                                .shadow(radius: 3)
                            
                            if revealedCells[index] {
                                if index == treasureLocation {
                                    Text("💰")
                                        .font(.title2)
                                } else {
                                    Text("❌")
                                        .foregroundColor(.white)
                                }
                            }
                        }
                    }
                    .disabled(revealedCells[index] || treasureFound)
                    .animation(.easeInOut(duration: 0.3), value: revealedCells[index])
                }
            }
            .padding(.horizontal)
            .padding(.top, 20)
            
            if treasureFound {
                Text("🎉 Tebrikler! Hazineyi buldun!")
                    .font(.title2)
                    .fontWeight(.bold)
                    .foregroundColor(.green)
                    .transition(.scale)
            } else {
                Text("Deneme sayısı: \(attempts)")
                    .font(.title3)
                    .padding(.top)
            }
            
            if showRestart {
                Button("Yeni Oyun") {
                    restartGame()
                }
                .padding()
                .background(Color.red)
                .foregroundColor(.white)
                .cornerRadius(10)
                .font(.headline)
                .padding(.top, 20)
            }
            
            Spacer()
        }
        .padding()
        .background(
            Image("treasure_map")
                .resizable()
                .scaledToFill()
                .opacity(0.3)
                .ignoresSafeArea()
        )
    }
    
    func restartGame() {
        treasureLocation = Int.random(in: 0..<36)
        revealedCells = Array(repeating: false, count: 36)
        attempts = 0
        treasureFound = false
        showRestart = false
    }
}

struct TreasureHuntGameView_Previews: PreviewProvider {
    static var previews: some View {
        TreasureHuntGameView()
    }
}

