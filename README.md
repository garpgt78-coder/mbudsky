--[[
    FISH IT - MOBILE AUTO FISHER
    Khusus Mobile/Android
    Auto cast + auto reel + rare fish ESP
    Full touch control
]]

loadstring(game:HttpGet("https://raw.githubusercontent.com/yourrepo/fishit_mobile/main.lua"))()

-- Atau paste langsung:

local FishItMobile = {
    AutoCast = true,
    AutoReel = true,
    RareOnly = false,
    ESP = true,
    CastDelay = 3,
    ReelDelay = 0.2
}

-- Services
local Players = game:GetService("Players")
local RunService = game:GetService("RunService")
local UserInputService = game:GetService("UserInputService")
local TweenService = game:GetService("TweenService")
local LocalPlayer = Players.LocalPlayer
local Mouse = LocalPlayer:GetMouse()

-- Touchscreen check
local isMobile = UserInputService.TouchEnabled and not UserInputService.KeyboardEnabled
print("[Mobile Mode]:", isMobile)

-- GUI Fullscreen buat mobile
local ScreenGui = Instance.new("ScreenGui")
ScreenGui.Name = "FishItMobile"
ScreenGui.Parent = game.CoreGui
ScreenGui.ResetOnSpawn = false

-- Main Frame - BESAR buat di-touch
local MainFrame = Instance.new("Frame")
MainFrame.Size = UDim2.new(0, 250, 0, 350)
MainFrame.Position = UDim2.new(0, 20, 0.3, 0) -- Pojok kiri
MainFrame.BackgroundColor3 = Color3.fromRGB(10, 20, 30)
MainFrame.BackgroundTransparency = 0.1
MainFrame.BorderSizePixel = 0
MainFrame.Active = true
MainFrame.Draggable = true
MainFrame.Parent = ScreenGui

local UICorner = Instance.new("UICorner")
UICorner.CornerRadius = UDim.new(0, 15)
UICorner.Parent = MainFrame

-- Title
local Title = Instance.new("TextLabel")
Title.Size = UDim2.new(1, 0, 0, 50)
Title.BackgroundColor3 = Color3.fromRGB(30, 144, 255)
Title.Text = "🎣 FISH IT MOBILE"
Title.TextColor3 = Color3.fromRGB(255, 255, 255)
Title.Font = Enum.Font.GothamBold
Title.TextSize = 22
Title.Parent = MainFrame

local UICorner2 = Instance.new("UICorner")
UICorner2.CornerRadius = UDim.new(0, 15)
UICorner2.Parent = Title

-- Status
local StatusFrame = Instance.new("Frame")
StatusFrame.Size = UDim2.new(1, -20, 0, 60)
StatusFrame.Position = UDim2.new(0, 10, 0, 60)
StatusFrame.BackgroundColor3 = Color3.fromRGB(30, 30, 40)
StatusFrame.BackgroundTransparency = 0.3
StatusFrame.Parent = MainFrame

local UICorner3 = Instance.new("UICorner")
UICorner3.CornerRadius = UDim.new(0, 10)
UICorner3.Parent = StatusFrame

local StatusText = Instance.new("TextLabel")
StatusText.Size = UDim2.new(1, 0, 1, 0)
StatusText.BackgroundTransparency = 1
StatusText.Text = "⚡ READY TO FISH"
StatusText.TextColor3 = Color3.fromRGB(0, 255, 100)
StatusText.Font = Enum.Font.GothamBold
StatusText.TextSize = 16
StatusText.Parent = StatusFrame

-- BUTTON BESAR buat mobile (Toggle Auto Cast)
local ToggleCastBtn = Instance.new("TextButton")
ToggleCastBtn.Size = UDim2.new(1, -20, 0, 60)
ToggleCastBtn.Position = UDim2.new(0, 10, 0, 130)
ToggleCastBtn.BackgroundColor3 = Color3.fromRGB(0, 150, 255)
ToggleCastBtn.Text = "🎣 AUTO CAST: ON"
ToggleCastBtn.TextColor3 = Color3.fromRGB(255, 255, 255)
ToggleCastBtn.Font = Enum.Font.GothamBold
ToggleCastBtn.TextSize = 20
ToggleCastBtn.Parent = MainFrame

local UICorner4 = Instance.new("UICorner")
UICorner4.CornerRadius = UDim.new(0, 40)
UICorner4.Parent = ToggleCastBtn

-- BUTTON Auto Reel
local ToggleReelBtn = Instance.new("TextButton")
ToggleReelBtn.Size = UDim2.new(1, -20, 0, 60)
ToggleReelBtn.Position = UDim2.new(0, 10, 0, 200)
ToggleReelBtn.BackgroundColor3 = Color3.fromRGB(0, 200, 100)
ToggleReelBtn.Text = "🎣 AUTO REEL: ON"
ToggleReelBtn.TextColor3 = Color3.fromRGB(255, 255, 255)
ToggleReelBtn.Font = Enum.Font.GothamBold
ToggleReelBtn.TextSize = 20
ToggleReelBtn.Parent = MainFrame

local UICorner5 = Instance.new("UICorner")
UICorner5.CornerRadius = UDim.new(0, 40)
UICorner5.Parent = ToggleReelBtn

-- BUTTON Rare Only
local ToggleRareBtn = Instance.new("TextButton")
ToggleRareBtn.Size = UDim2.new(1, -20, 0, 50)
ToggleRareBtn.Position = UDim2.new(0, 10, 0, 270)
ToggleRareBtn.BackgroundColor3 = Color3.fromRGB(255, 150, 0)
ToggleRareBtn.Text = "⭐ RARE ONLY: OFF"
ToggleRareBtn.TextColor3 = Color3.fromRGB(255, 255, 255)
ToggleRareBtn.Font = Enum.Font.GothamBold
ToggleRareBtn.TextSize = 18
ToggleRareBtn.Parent = MainFrame

local UICorner6 = Instance.new("UICorner")
UICorner6.CornerRadius = UDim.new(0, 30)
UICorner6.Parent = ToggleRareBtn

-- Stats Display
local StatsFrame = Instance.new("Frame")
StatsFrame.Size = UDim2.new(1, -20, 0, 60)
StatsFrame.Position = UDim2.new(0, 10, 0, 330)
StatsFrame.BackgroundColor3 = Color3.fromRGB(20, 20, 30)
StatsFrame.BackgroundTransparency = 0.3
StatsFrame.Parent = MainFrame

local UICorner7 = Instance.new("UICorner")
UICorner7.CornerRadius = UDim.new(0, 10)
UICorner7.Parent = StatsFrame

local StatsText = Instance.new("TextLabel")
StatsText.Size = UDim2.new(1, 0, 1, 0)
StatsText.BackgroundTransparency = 1
StatsText.Text = "⭐ Rare: 0\n🐟 Total: 0"
StatsText.TextColor3 = Color3.fromRGB(255, 255, 255)
StatsText.Font = Enum.Font.Gotham
StatsText.TextSize = 16
StatsText.TextXAlignment = Enum.TextXAlignment.Center
StatsText.Parent = StatsFrame

-- BUTTON Close (X besar)
local CloseBtn = Instance.new("TextButton")
CloseBtn.Size = UDim2.new(0, 50, 0, 50)
CloseBtn.Position = UDim2.new(1, -60, 0, 5)
CloseBtn.BackgroundColor3 = Color3.fromRGB(255, 50, 50)
CloseBtn.Text = "✕"
CloseBtn.TextColor3 = Color3.fromRGB(255, 255, 255)
CloseBtn.Font = Enum.Font.GothamBold
CloseBtn.TextSize = 30
CloseBtn.Parent = MainFrame

local UICorner8 = Instance.new("UICorner")
UICorner8.CornerRadius = UDim.new(0, 25)
UICorner8.Parent = CloseBtn

-- ===== FISH IT SPECIFIC FUNCTIONS =====

-- Find fishing rod
local function getFishingRod()
    local character = LocalPlayer.Character
    if not character then return nil end
    
    -- Cari di tangan
    for _, tool in ipairs(character:GetChildren()) do
        if tool:IsA("Tool") and (tool.Name:lower():find("rod") or tool.Name:lower():find("fish")) then
            return tool
        end
    end
    
    -- Cari di backpack
    local backpack = LocalPlayer:FindFirstChild("Backpack")
    if backpack then
        for _, tool in ipairs(backpack:GetChildren()) do
            if tool:IsA("Tool") and (tool.Name:lower():find("rod") or tool.Name:lower():find("fish")) then
                return tool
            end
        end
    end
    
    return nil
end

-- Auto cast
local function autoCast()
    if not FishItMobile.AutoCast then return end
    
    local rod = getFishingRod()
    if rod then
        -- Equip rod
        LocalPlayer.Character.Humanoid:EquipTool(rod)
        task.wait(0.3)
        
        -- Activate tool (tap screen di tengah)
        local vim = game:GetService("VirtualInputManager")
        local viewportSize = workspace.CurrentCamera.ViewportSize
        
        -- Simulate tap di tengah layar
        vim:SendTouchEvent(0, viewportSize.X/2, viewportSize.Y/2, 0, Enum.UserInputState.Begin, game)
        task.wait(0.1)
        vim:SendTouchEvent(0, viewportSize.X/2, viewportSize.Y/2, 0, Enum.UserInputState.End, game)
    end
end

-- Find bobber
local function findBobber()
    for _, obj in ipairs(workspace:GetDescendants()) do
        if obj:IsA("Part") and (obj.Name:lower():find("bobber") or obj.Name:lower():find("float")) then
            return obj
        end
    end
    return nil
end

-- Check rare fish
local function isRareFish(obj)
    if not obj then return false end
    
    local rareNames = {"legendary", "mythic", "shiny", "golden", "crystal", "magma", "void", "ancient"}
    
    for _, name in ipairs(rareNames) do
        if obj.Name:lower():find(name) or (obj.Parent and obj.Parent.Name:lower():find(name)) then
            return true
        end
    end
    
    -- Check color (golden)
    if obj:IsA("BasePart") then
        local r, g, b = obj.Color.R, obj.Color.G, obj.Color.B
        if r > 0.8 and g > 0.6 and b < 0.3 then
            return true
        end
    end
    
    return false
end

-- Auto reel
local function autoReel()
    if not FishItMobile.AutoReel then return end
    
    local bobber = findBobber()
    if not bobber then return end
    
    -- Check if fish biting (bobber moving down)
    if bobber.Velocity.Y < -1 or bobber.Position.Y < (bobber.Parent and bobber.Parent.Position.Y or 0) - 3 then
        
        -- Rare only mode
        if FishItMobile.RareOnly then
            local fishPart = bobber:FindFirstChild("Fish") or bobber.Parent:FindFirstChild("Fish")
            if not isRareFish(fishPart) then
                return
            end
        end
        
        task.wait(FishItMobile.ReelDelay)
        
        -- Tap bobber
        local vim = game:GetService("VirtualInputManager")
        local pos, onScreen = workspace.CurrentCamera:WorldToViewportPoint(bobber.Position)
        
        if onScreen then
            vim:SendTouchEvent(0, pos.X, pos.Y, 0, Enum.UserInputState.Begin, game)
            task.wait(0.05)
            vim:SendTouchEvent(0, pos.X, pos.Y, 0, Enum.UserInputState.End, game)
        end
        
        -- Update stats
        if isRareFish(bobber) then
            stats.rare = (stats.rare or 0) + 1
        end
        stats.total = (stats.total or 0) + 1
        
        -- Update GUI
        StatsText.Text = string.format("⭐ Rare: %d\n🐟 Total: %d", stats.rare or 0, stats.total or 0)
    end
end

-- ESP untuk mobile (pake BillboardGui)
local function createMobileESP(obj, isRare)
    if not FishItMobile.ESP then return end
    
    local billboard = Instance.new("BillboardGui")
    billboard.Size = UDim2.new(0, 150, 0, 50)
    billboard.StudsOffset = Vector3.new(0, 2, 0)
    billboard.AlwaysOnTop = true
    billboard.Parent = obj
    
    local text = Instance.new("TextLabel")
    text.Size = UDim2.new(1, 0, 1, 0)
    text.BackgroundTransparency = 1
    text.Text = isRare and "⭐ RARE ⭐" or "🐟 FISH"
    text.TextColor3 = isRare and Color3.fromRGB(255, 0, 0) or Color3.fromRGB(255, 255, 255)
    text.TextStrokeColor3 = Color3.fromRGB(0, 0, 0)
    text.TextStrokeTransparency = 0.3
    text.Font = Enum.Font.GothamBold
    text.TextSize = 24
    text.Parent = billboard
    
    return billboard
end

-- ===== BUTTON CLICK HANDLERS (MOBILE TOUCH) =====

-- Toggle Auto Cast
ToggleCastBtn.MouseButton1Click:Connect(function()
    FishItMobile.AutoCast = not FishItMobile.AutoCast
    ToggleCastBtn.Text = FishItMobile.AutoCast and "🎣 AUTO CAST: ON" or "🎣 AUTO CAST: OFF"
    ToggleCastBtn.BackgroundColor3 = FishItMobile.AutoCast and Color3.fromRGB(0, 150, 255) or Color3.fromRGB(100, 100, 100)
    StatusText.Text = FishItMobile.AutoCast and "⚡ AUTO FISHING..." or "⏸ PAUSED"
    StatusText.TextColor3 = FishItMobile.AutoCast and Color3.fromRGB(0, 255, 100) or Color3.fromRGB(255, 200, 0)
end)

-- Toggle Auto Reel
ToggleReelBtn.MouseButton1Click:Connect(function()
    FishItMobile.AutoReel = not FishItMobile.AutoReel
    ToggleReelBtn.Text = FishItMobile.AutoReel and "🎣 AUTO REEL: ON" or "🎣 AUTO REEL: OFF"
    ToggleReelBtn.BackgroundColor3 = FishItMobile.AutoReel and Color3.fromRGB(0, 200, 100) or Color3.fromRGB(100, 100, 100)
end)

-- Toggle Rare Only
ToggleRareBtn.MouseButton1Click:Connect(function()
    FishItMobile.RareOnly = not FishItMobile.RareOnly
    ToggleRareBtn.Text = FishItMobile.RareOnly and "⭐ RARE ONLY: ON" or "⭐ RARE ONLY: OFF"
    ToggleRareBtn.BackgroundColor3 = FishItMobile.RareOnly and Color3.fromRGB(255, 100, 0) or Color3.fromRGB(255, 150, 0)
end)

-- Close button
CloseBtn.MouseButton1Click:Connect(function()
    ScreenGui:Destroy()
    print("[FishIt] Unloaded")
end)

-- ===== MAIN LOOP =====
local lastCastTime = 0
local stats = {rare = 0, total = 0}

RunService.Heartbeat:Connect(function(dt)
    local currentTime = tick()
    
    -- Auto cast setiap 3 detik
    if FishItMobile.AutoCast and currentTime - lastCastTime > FishItMobile.CastDelay then
        pcall(autoCast)
        lastCastTime = currentTime
    end
    
    -- Auto reel
    if FishItMobile.AutoReel then
        pcall(autoReel)
    end
    
    -- ESP
    if FishItMobile.ESP then
        for _, obj in ipairs(workspace:GetDescendants()) do
            if obj:IsA("Part") and obj.Name:lower():find("bobber") and not obj:FindFirstChildOfClass("BillboardGui") then
                local isRare = isRareFish(obj)
                if not FishItMobile.RareOnly or isRare then
                    createMobileESP(obj, isRare)
                end
            end
        end
    end
end)

-- Auto equip rod saat spawn
LocalPlayer.CharacterAdded:Connect(function(char)
    task.wait(1)
    local rod = getFishingRod()
    if rod and FishItMobile.AutoCast then
        char.Humanoid:EquipTool(rod)
    end
end)

-- Anti-AFK mobile
local vu = game:GetService("VirtualUser")
LocalPlayer.Idled:Connect(function()
    vu:Button2Down(Vector2.new(0,0), workspace.CurrentCamera.CFrame)
    wait(1)
    vu:Button2Up(Vector2.new(0,0), workspace.CurrentCamera.CFrame)
end)

print([[
══════════════════════════════════
   📱 FISH IT MOBILE LOADED     
══════════════════════════════════
   ✓ Touch optimized
   ✓ Tombol gede-gede
   ✓ Auto cast & reel
   ✓ Rare fish ESP
   
   TEKAN TOMBOL DI LAYAR!
══════════════════════════════════
]])
