function buka()
  local args = {
	"Valentine Event",
	3
}
game:GetService("ReplicatedStorage"):WaitForChild("6e3def40-5155-42d2-b3a0-ca4391f39e04"):WaitForChild("Functions"):WaitForChild("OpenEgg"):InvokeServer(unpack(args))
end

while true do
  buka()
  task.wait(0.1)
end