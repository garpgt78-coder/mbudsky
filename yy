local Rayfield = loadstring(game:HttpGet('https://sirius.menu/rayfield'))()

local Window = Rayfield:CreateWindow({
   Name = "Auto Egg Opener v2",
   LoadingTitle = "Menyiapkan Script...",
   LoadingSubtitle = "by Gemini",
   ConfigurationSaving = {
      Enabled = true,
      FolderName = "GeminiScripts",
      FileName = "EggOpenerConfig"
   }
})

-- Variabel Global untuk Kontrol
local Settings = {
    loop = false,
    delay = 0.5
}

-- Fungsi Utama untuk Remote Baru
local function buka()
    local args = {
        "Valentine Event",
        3
    }
    
    -- Menggunakan ID baru yang kamu berikan
    local targetRemote = game:GetService("ReplicatedStorage"):WaitForChild("9e1f9495-9794-4405-a17a-41d2592a826c", 5)
    
    if targetRemote then
        targetRemote:WaitForChild("Functions"):WaitForChild("OpenEgg"):InvokeServer(unpack(args))
    else
        warn("Remote Storage tidak ditemukan! Cek kembali ID-nya.")
    end
end

-- Tab Settings
local MainTab = Window:CreateTab("Utama", 4483362458)

-- Toggle untuk On/Off
MainTab:CreateToggle({
   Name = "Mulai Auto Open",
   CurrentValue = false,
   Flag = "EggToggle", 
   Callback = function(Value)
      Settings.loop = Value
      if Value then
          task.spawn(function()
              while Settings.loop do
                  buka()
                  task.wait(Settings.delay)
              end
          end)
      end
   end,
})

-- Slider untuk Jeda (task.wait)
MainTab:CreateSlider({
   Name = "Kecepatan Jeda (Detik)",
   Range = {0.1, 10},
   Increment = 0.1,
   Suffix = " detik",
   CurrentValue = 0.5,
   Flag = "DelaySlider",
   Callback = function(Value)
      Settings.delay = Value
   end,
})

Rayfield:Notify({
   Title = "Script Berhasil Dimuat",
   Content = "Remote ID telah diperbarui. Selamat mencoba!",
   Duration = 5,
   Image = 4483362458,
})
