//
//  SettingsView.swift
//  ArX Game
//
//  Created by Burak Bozoğlu on 17.04.2025.
//

import SwiftUI

struct SettingsView: View {
    @State private var isSoundOn = true
    @State private var brightness: Double = 0.5
    @State private var isNotificationsOn = true
    @State private var selectedTheme = "Açık"
    
    @State private var timeSpent = 0
    @State private var timer = Timer.publish(every: 1, on: .main, in: .common).autoconnect()

    var body: some View {
        NavigationStack {
            ZStack {
                LinearGradient(colors: [.purple.opacity(0.6), .black], startPoint: .topLeading, endPoint: .bottomTrailing)
                    .ignoresSafeArea()

                ScrollView {
                    VStack(spacing: 30) {
                        Text("⚙️ Ayarlar")
                            .font(.system(size: 40, weight: .bold))
                            .foregroundColor(.white)
                            .shadow(radius: 5)
                            .padding(.top, 40)

                        // Ses Ayarı
                        settingCard {
                            HStack {
                                Image(systemName: isSoundOn ? "speaker.wave.2.fill" : "speaker.slash.fill")
                                    .foregroundColor(.orange)
                                    .font(.title)
                                Text("Oyun Sesi")
                                    .font(.title3.bold())
                                Spacer()
                                Toggle("", isOn: $isSoundOn)
                                    .labelsHidden()
                            }
                        }

                        // Parlaklık Ayarı
                        settingCard {
                            VStack(alignment: .leading, spacing: 10) {
                                HStack {
                                    Image(systemName: "sun.max.fill")
                                        .foregroundColor(.yellow)
                                    Text("Ekran Parlaklığı")
                                        .font(.title3.bold())
                                }
                                Slider(value: $brightness)
                            }
                        }

                        // Uygulamada Kalınan Süre
                        settingCard {
                            HStack {
                                Image(systemName: "clock.arrow.circlepath")
                                    .foregroundColor(.blue)
                                Text("Kullanım Süresi")
                                    .font(.title3.bold())
                                Spacer()
                                Text("\(timeSpent) sn")
                                    .font(.title3)
                                    .foregroundColor(.gray)
                            }
                        }

                        // Bildirimler
                        settingCard {
                            HStack {
                                Image(systemName: isNotificationsOn ? "bell.fill" : "bell.slash.fill")
                                    .foregroundColor(.green)
                                Text("Bildirimler")
                                    .font(.title3.bold())
                                Spacer()
                                Toggle("", isOn: $isNotificationsOn)
                                    .labelsHidden()
                            }
                        }

                        // Tema Seçimi
                        settingCard {
                            HStack {
                                Image(systemName: "paintbrush")
                                    .foregroundColor(.pink)
                                Text("Tema")
                                    .font(.title3.bold())
                                Spacer()
                                Picker("Tema", selection: $selectedTheme) {
                                    Text("Açık").tag("Açık")
                                    Text("Koyu").tag("Koyu")
                                }
                                .pickerStyle(.segmented)
                                .frame(width: 160)
                            }
                        }

                        // Geri Dön Butonu
                        Button(action: {
                            // Sayfayı kapat
                        }) {
                            Text("🔙 Geri Dön")
                                .font(.title2.bold())
                                .padding()
                                .frame(maxWidth: .infinity)
                                .background(Color.white.opacity(0.2))
                                .foregroundColor(.white)
                                .cornerRadius(16)
                                .shadow(radius: 5)
                        }
                        .padding(.top, 10)
                    }
                    .padding(.horizontal)
                    .padding(.bottom, 40)
                }
            }
            .onReceive(timer) { _ in
                timeSpent += 1
            }
        }
    }

    // 🔹 Ortak Ayar Kartı
    func settingCard<Content: View>(@ViewBuilder content: () -> Content) -> some View {
        content()
            .padding()
            .background(.ultraThinMaterial)
            .cornerRadius(20)
            .shadow(radius: 5)
    }
}

#Preview {
    SettingsView()
}
