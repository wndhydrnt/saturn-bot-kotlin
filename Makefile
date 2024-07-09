PROTOCOL_VERSION?=v0.9.0

src/main/proto/grpc_controller.proto:
	mkdir -p src/main/proto
	curl -L -o src/main/proto/grpc_controller.proto --silent --fail https://raw.githubusercontent.com/hashicorp/go-plugin/v1.6.0/internal/plugin/grpc_controller.proto
	echo 'option java_package = "net.saturnbot.plugin.protocol";' >> src/main/proto/grpc_controller.proto

src/main/proto/saturnbot.proto:
	mkdir -p src/main/proto
	curl -L -o src/main/proto/saturnbot.proto --silent --fail https://raw.githubusercontent.com/wndhydrnt/saturn-bot-protocol/$(PROTOCOL_VERSION)/protocol/v1/saturnbot.proto
	echo 'option java_package = "net.saturnbot.plugin.protocol";' >> src/main/proto/saturnbot.proto
