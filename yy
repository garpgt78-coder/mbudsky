local Rayfield = loadstring(game:HttpGet('https://sirius.menu/rayfield'))()

local Window = Rayfield:CreateWindow({
   Name = "Auto Egg Opener",
   LoadingTitle = "Loading Script...",
   LoadingSubtitle = "by Gemini",
   ConfigurationSaving = {
      Enabled = true,
      FolderName = "GeminiScripts",
      FileName = "EggOpenerConfig"
   }
})

-- Variabel Default
local _G = {
    loop = false,
    delay = 0.5
}

-- Fungsi Utama
local function buka()
    local args = {
        "Valentine Event",
        3
    }
    local remote = game:GetService("ReplicatedStorage"):FindFirstChild("6e3def40-5155-42d2-b3a0-ca4391f39e04")
    if remote then
        remote:WaitForChild("Functions"):WaitForChild("OpenEgg"):InvokeServer(unpack(args))
    end
end

-- Tab Utama
local MainTab = Window:CreateTab("Settings", 4483362458) -- Icon ID

-- Toggle On/Off
local Toggle = MainTab:CreateToggle({
   Name = "Auto Open Egg",
   CurrentValue = false,
   Flag = "EggToggle", 
   Callback = function(Value)
      _G.loop = Value
      if Value then
          -- Menjalankan loop di thread terpisah agar GUI tidak freeze
          task.spawn(function()
              while _G.loop do
                  buka()
                  task.wait(_G.delay)
              end
          end)
      end
   end,
})

-- Slider untuk Jeda (task.wait)
local Slider = MainTab:CreateSlider({
   Name = "Delay Speed (Detik)",
   Range = {0.1, 5},
   Increment = 0.1,
   Suffix = "s",
   CurrentValue = 0.5,
   Flag = "DelaySlider",
   Callback = function(Value)
      _G.delay = Value
   end,
})

Rayfield:Notify({
   Title = "Script Ready!",
   Content = "Atur delay dan nyalakan toggle untuk memulai.",
   Duration = 5,
   Image = 4483362458,
})
