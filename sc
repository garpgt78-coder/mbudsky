--[[
    Craft a World - Auto Farm Script
    DIBBOT Edition v2.0
    Fitur: Auto mine, auto craft, auto collect, ESP
    Hotkey: F6 (Toggle GUI), F7 (Toggle Auto Farm)
]]

loadstring(game:HttpGet("https://raw.githubusercontent.com/dibbot/craftaworld/main/main.lua"))()

-- Atau paste langsung:

local CraftWorld = {
    AutoMine = true,
    AutoCollect = true,
    AutoCraft = true,
    AutoSmelt = true,
    AutoChop = true,
    ESP = true,
    Radius = 30,
    FarmMode = "Mine" -- Mine, Chop, Hunt
}

-- Services
local Players = game:GetService("Players")
local RunService = game:GetService("RunService")
local UserInputService = game:GetService("UserInputService")
local TweenService = game:GetService("TweenService")
local LocalPlayer = Players.LocalPlayer
local Character = LocalPlayer.Character or LocalPlayer.CharacterAdded:Wait()
local Humanoid = Character:WaitForChild("Humanoid")
local HumanoidRootPart = Character:WaitForChild("HumanoidRootPart")

-- GUI
local ScreenGui = Instance.new("ScreenGui")
ScreenGui.Name = "CraftWorldScript"
ScreenGui.Parent = game.CoreGui

local MainFrame = Instance.new("Frame")
MainFrame.Size = UDim2.new(0, 350, 0, 450)
MainFrame.Position = UDim2.new(0.5, -175, 0.5, -225)
MainFrame.BackgroundColor3 = Color3.fromRGB(20, 20, 30)
MainFrame.BackgroundTransparency = 0.1
MainFrame.BorderSizePixel = 0
MainFrame.Active = true
MainFrame.Draggable = true
MainFrame.Parent = ScreenGui

local UICorner = Instance.new("UICorner")
UICorner.CornerRadius = UDim.new(0, 10)
UICorner.Parent = MainFrame

local Title = Instance.new("TextLabel")
Title.Size = UDim2.new(1, 0, 0, 40)
Title.BackgroundColor3 = Color3.fromRGB(30, 144, 255)
Title.Text = "⚒️ CRAFT A WORLD - DIBBOT"
Title.TextColor3 = Color3.fromRGB(255, 255, 255)
Title.Font = Enum.Font.GothamBold
Title.TextSize = 18
Title.Parent = MainFrame

local UICorner2 = Instance.new("UICorner")
UICorner2.CornerRadius = UDim.new(0, 10)
UICorner2.Parent = Title

-- Status
local StatusFrame = Instance.new("Frame")
StatusFrame.Size = UDim2.new(1, -20, 0, 60)
StatusFrame.Position = UDim2.new(0, 10, 0, 50)
StatusFrame.BackgroundColor3 = Color3.fromRGB(30, 30, 40)
StatusFrame.BackgroundTransparency = 0.3
StatusFrame.Parent = MainFrame

local UICorner3 = Instance.new("UICorner")
UICorner3.CornerRadius = UDim.new(0, 8)
UICorner3.Parent = StatusFrame

local StatusText = Instance.new("TextLabel")
StatusText.Size = UDim2.new(1, 0, 0.5, 0)
StatusText.Position = UDim2.new(0, 0, 0, 0)
StatusText.BackgroundTransparency = 1
StatusText.Text = "⛏️ AUTO MINE: ON"
StatusText.TextColor3 = Color3.fromRGB(0, 255, 100)
StatusText.Font = Enum.Font.GothamBold
StatusText.TextSize = 16
StatusText.Parent = StatusFrame

local ModeText = Instance.new("TextLabel")
ModeText.Size = UDim2.new(1, 0, 0.5, 0)
ModeText.Position = UDim2.new(0, 0, 0.5, 0)
ModeText.BackgroundTransparency = 1
ModeText.Text = "Mode: MINE"
ModeText.TextColor3 = Color3.fromRGB(255, 255, 0)
ModeText.Font = Enum.Font.Gotham
StatusText.TextSize = 14
ModeText.Parent = StatusFrame

-- Buttons
local function createButton(name, pos, color, callback)
    local btn = Instance.new("TextButton")
    btn.Size = UDim2.new(0.5, -15, 0, 40)
    btn.Position = pos
    btn.BackgroundColor3 = color
    btn.Text = name
    btn.TextColor3 = Color3.fromRGB(255, 255, 255)
    btn.Font = Enum.Font.GothamBold
    btn.TextSize = 14
    btn.Parent = MainFrame
    
    local btnCorner = Instance.new("UICorner")
    btnCorner.CornerRadius = UDim.new(0, 8)
    btnCorner.Parent = btn
    
    btn.MouseButton1Click:Connect(callback)
    return btn
end

-- Toggle Auto Mine
local mineBtn = createButton("⛏️ MINE", UDim2.new(0, 10, 0, 120), Color3.fromRGB(0, 150, 255), function()
    CraftWorld.AutoMine = not CraftWorld.AutoMine
    mineBtn.Text = CraftWorld.AutoMine and "⛏️ MINE: ON" or "⛏️ MINE: OFF"
    mineBtn.BackgroundColor3 = CraftWorld.AutoMine and Color3.fromRGB(0, 150, 255) or Color3.fromRGB(80, 80, 80)
    updateStatus()
end)

-- Toggle Auto Collect
local collectBtn = createButton("📦 COLLECT", UDim2.new(0.5, 5, 0, 120), Color3.fromRGB(0, 200, 100), function()
    CraftWorld.AutoCollect = not CraftWorld.AutoCollect
    collectBtn.Text = CraftWorld.AutoCollect and "📦 COLLECT: ON" or "📦 COLLECT: OFF"
    collectBtn.BackgroundColor3 = CraftWorld.AutoCollect and Color3.fromRGB(0, 200, 100) or Color3.fromRGB(80, 80, 80)
end)

-- Toggle Auto Craft
local craftBtn = createButton("🔨 CRAFT", UDim2.new(0, 10, 0, 170), Color3.fromRGB(255, 150, 0), function()
    CraftWorld.AutoCraft = not CraftWorld.AutoCraft
    craftBtn.Text = CraftWorld.AutoCraft and "🔨 CRAFT: ON" or "🔨 CRAFT: OFF"
    craftBtn.BackgroundColor3 = CraftWorld.AutoCraft and Color3.fromRGB(255, 150, 0) or Color3.fromRGB(80, 80, 80)
end)

-- Mode Selector
local modeBtn = createButton("🔄 SWITCH MODE", UDim2.new(0.5, 5, 0, 170), Color3.fromRGB(150, 0, 255), function()
    local modes = {"Mine", "Chop", "Hunt"}
    local current = 0
    for i, m in ipairs(modes) do
        if m == CraftWorld.FarmMode then
            current = i
            break
        end
    end
    local next = current % #modes + 1
    CraftWorld.FarmMode = modes[next]
    ModeText.Text = "Mode: " .. CraftWorld.FarmMode
    modeBtn.Text = "🔄 MODE: " .. CraftWorld.FarmMode
end)

-- ESP Toggle
local espBtn = createButton("👁️ ESP", UDim2.new(0, 10, 0, 220), Color3.fromRGB(255, 0, 255), function()
    CraftWorld.ESP = not CraftWorld.ESP
    espBtn.Text = CraftWorld.ESP and "👁️ ESP: ON" or "👁️ ESP: OFF"
    espBtn.BackgroundColor3 = CraftWorld.ESP and Color3.fromRGB(255, 0, 255) or Color3.fromRGB(80, 80, 80)
    if not CraftWorld.ESP then
        clearESP()
    end
end)

-- Radius Slider
local radiusSlider = Instance.new("Frame")
radiusSlider.Size = UDim2.new(1, -20, 0, 60)
radiusSlider.Position = UDim2.new(0, 10, 0, 270)
radiusSlider.BackgroundColor3 = Color3.fromRGB(30, 30, 40)
radiusSlider.BackgroundTransparency = 0.3
radiusSlider.Parent = MainFrame

local UICorner4 = Instance.new("UICorner")
UICorner4.CornerRadius = UDim.new(0, 8)
UICorner4.Parent = radiusSlider

local radiusLabel = Instance.new("TextLabel")
radiusLabel.Size = UDim2.new(1, -20, 0, 30)
radiusLabel.Position = UDim2.new(0, 10, 0, 5)
radiusLabel.BackgroundTransparency = 1
radiusLabel.Text = "Radius: 30"
radiusLabel.TextColor3 = Color3.fromRGB(255, 255, 255)
radiusLabel.Font = Enum.Font.Gotham
radiusLabel.TextSize = 14
radiusLabel.TextXAlignment = Enum.TextXAlignment.Left
radiusLabel.Parent = radiusSlider

local radiusBar = Instance.new("Frame")
radiusBar.Size = UDim2.new(1, -20, 0, 10)
radiusBar.Position = UDim2.new(0, 10, 0, 40)
radiusBar.BackgroundColor3 = Color3.fromRGB(50, 50, 60)
radiusBar.Parent = radiusSlider

local radiusFill = Instance.new("Frame")
radiusFill.Size = UDim2.new(CraftWorld.Radius / 50, 0, 1, 0)
radiusFill.BackgroundColor3 = Color3.fromRGB(0, 150, 255)
radiusFill.Parent = radiusBar

-- Close button
local closeBtn = Instance.new("TextButton")
closeBtn.Size = UDim2.new(0, 30, 0, 30)
closeBtn.Position = UDim2.new(1, -35, 0, 5)
closeBtn.BackgroundColor3 = Color3.fromRGB(255, 50, 50)
closeBtn.Text = "✕"
closeBtn.TextColor3 = Color3.fromRGB(255, 255, 255)
closeBtn.Font = Enum.Font.GothamBold
closeBtn.TextSize = 16
closeBtn.Parent = MainFrame

local UICorner5 = Instance.new("UICorner")
UICorner5.CornerRadius = UDim.new(0, 8)
UICorner5.Parent = closeBtn

closeBtn.MouseButton1Click:Connect(function()
    ScreenGui:Destroy()
    print("[CraftWorld] Script unloaded")
end)

-- Functions
local ESPObjects = {}

function updateStatus()
    StatusText.Text = CraftWorld.AutoMine and "⛏️ AUTO MINE: ON" or "⛏️ AUTO MINE: OFF"
    StatusText.TextColor3 = CraftWorld.AutoMine and Color3.fromRGB(0, 255, 100) or Color3.fromRGB(255, 100, 100)
end

function clearESP()
    for _, obj in pairs(ESPObjects) do
        obj:Destroy()
    end
    table.clear(ESPObjects)
end

function createESP(obj, color, name)
    if not CraftWorld.ESP or not obj then return end
    
    local highlight = Instance.new("Highlight")
    highlight.FillColor = color
    highlight.OutlineColor = color
    highlight.FillTransparency = 0.5
    highlight.OutlineTransparency = 0
    highlight.Parent = obj
    highlight.Adornee = obj
    table.insert(ESPObjects, highlight)
    
    local billboard = Instance.new("BillboardGui")
    billboard.Size = UDim2.new(0, 100, 0, 30)
    billboard.StudsOffset = Vector3.new(0, 2, 0)
    billboard.AlwaysOnTop = true
    billboard.Parent = obj
    
    local text = Instance.new("TextLabel")
    text.Size = UDim2.new(1, 0, 1, 0)
    text.BackgroundTransparency = 1
    text.Text = name or "Resource"
    text.TextColor3 = color
    text.TextStrokeColor3 = Color3.fromRGB(0, 0, 0)
    text.TextStrokeTransparency = 0.3
    text.Font = Enum.Font.GothamBold
    text.TextSize = 14
    text.Parent = billboard
    
    table.insert(ESPObjects, billboard)
end

-- Auto Farm Logic
function findResources()
    local resources = {}
    local radius = CraftWorld.Radius
    
    for _, obj in ipairs(workspace:GetDescendants()) do
        if obj:IsA("BasePart") and obj.Position and HumanoidRootPart then
            local dist = (obj.Position - HumanoidRootPart.Position).Magnitude
            if dist <= radius then
                local name = obj.Name:lower()
                
                -- Mining resources (ores, stones)
                if CraftWorld.FarmMode == "Mine" and (name:find("ore") or name:find("stone") or name:find("rock") or name:find("mineral")) then
                    table.insert(resources, {obj = obj, dist = dist})
                    createESP(obj, Color3.fromRGB(255, 255, 0), "ORE")
                    
                -- Wood resources
                elseif CraftWorld.FarmMode == "Chop" and (name:find("tree") or name:find("wood") or name:find("log") or name:find("branch")) then
                    table.insert(resources, {obj = obj, dist = dist})
                    createESP(obj, Color3.fromRGB(0, 255, 0), "WOOD")
                    
                -- Hunt resources (animals)
                elseif CraftWorld.FarmMode == "Hunt" and (name:find("animal") or name:find("cow") or name:find("chicken") or name:find("pig") or name:find("sheep")) then
                    table.insert(resources, {obj = obj, dist = dist})
                    createESP(obj, Color3.fromRGB(255, 0, 0), "ANIMAL")
                end
            end
        end
    end
    
    table.sort(resources, function(a, b) return a.dist < b.dist end)
    return resources
end

function collectDrops()
    if not CraftWorld.AutoCollect then return end
    
    for _, obj in ipairs(workspace:GetDescendants()) do
        if obj:IsA("BasePart") and obj.Name:lower():find("drop") or obj.Name:lower():find("item") then
            local dist = (obj.Position - HumanoidRootPart.Position).Magnitude
            if dist <= CraftWorld.Radius then
                -- Move to drop
                local tween = TweenService:Create(HumanoidRootPart, TweenInfo.new(0.5), {CFrame = CFrame.new(obj.Position + Vector3.new(0, 3, 0))})
                tween:Play()
                tween.Completed:Wait()
                
                -- Collect (fire touch)
                if obj:FindFirstChildOfClass("TouchTransmitter") then
                    firetouchinterest(HumanoidRootPart, obj, 0)
                    task.wait(0.1)
                    firetouchinterest(HumanoidRootPart, obj, 1)
                end
            end
        end
    end
end

function autoCraft()
    if not CraftWorld.AutoCraft then return end
    
    -- Look for crafting stations
    for _, obj in ipairs(workspace:GetDescendants()) do
        if obj:IsA("Model") and (obj.Name:lower():find("craft") or obj.Name:lower():find("bench") or obj.Name:lower():find("furnace")) then
            local stationPart = obj.PrimaryPart or obj:FindFirstChildOfClass("BasePart")
            if stationPart then
                local dist = (HumanoidRootPart.Position - stationPart.Position).Magnitude
                if dist <= 10 then
                    -- Find craft button
                    local gui = obj:FindFirstChildOfClass("SurfaceGui") or obj:FindFirstChildOfClass("BillboardGui")
                    if gui then
                        for _, btn in ipairs(gui:GetDescendants()) do
                            if btn:IsA("TextButton") and (btn.Text:lower():find("craft") or btn.Name:lower():find("craft")) then
                                fireclickdetector(btn:FindFirstChildOfClass("ClickDetector"))
                                task.wait(1)
                            end
                        end
                    end
                end
            end
        end
    end
end

-- Main loop
RunService.Heartbeat:Connect(function()
    if not CraftWorld.AutoMine then return end
    
    local resources = findResources()
    
    if #resources > 0 then
        local target = resources[1].obj
        local targetPos = target.Position
        
        -- Move to resource
        local tween = TweenService:Create(HumanoidRootPart, TweenInfo.new(0.3), {CFrame = CFrame.new(targetPos + Vector3.new(0, 3, 0))})
        tween:Play()
        tween.Completed:Wait()
        
        -- Mine/Chop/Hunt
        local tool = Character:FindFirstChildOfClass("Tool")
        if tool then
            tool:Activate()
            task.wait(0.5)
            tool:Deactivate()
        else
            -- Use hands (click)
            local vim = game:GetService("VirtualInputManager")
            vim:SendMouseButtonEvent(targetPos.X, targetPos.Y, 0, true, game, 1)
            task.wait(0.1)
            vim:SendMouseButtonEvent(targetPos.X, targetPos.Y, 0, false, game, 1)
        end
        
        task.wait(0.5)
    end
    
    -- Auto collect drops
    collectDrops()
    
    -- Auto craft
    autoCraft()
end)

-- Hotkey toggle
UserInputService.InputBegan:Connect(function(input)
    if input.KeyCode == Enum.KeyCode.F6 then
        MainFrame.Visible = not MainFrame.Visible
    elseif input.KeyCode == Enum.KeyCode.F7 then
        CraftWorld.AutoMine = not CraftWorld.AutoMine
        updateStatus()
    end
end)

-- Anti-AFK
local vu = game:GetService("VirtualUser")
LocalPlayer.Idled:Connect(function()
    vu:Button2Down(Vector2.new(0,0), workspace.CurrentCamera.CFrame)
    task.wait(1)
    vu:Button2Up(Vector2.new(0,0), workspace.CurrentCamera.CFrame)
end)

print([[
╔══════════════════════════════════╗
║   CRAFT A WORLD - DIBBOT EDITION ║
╠══════════════════════════════════╣
║ F6 - Toggle GUI                  ║
║ F7 - Toggle Auto Farm            ║
║                                  ║
║ Modes: Mine | Chop | Hunt        ║
╚══════════════════════════════════╝
]])