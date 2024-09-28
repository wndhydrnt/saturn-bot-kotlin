PROTOCOL_VERSION?=v0.11.4
INTEGRATION_TEST_BIN=integration-test-$(PROTOCOL_VERSION).$(shell uname -s)-$(shell uname -m)
INTEGRATION_TEST_PATH?=integration_test/$(INTEGRATION_TEST_BIN)
SATURN_BOT_BIN_PATH?=saturn-bot

clean:
	rm -f src/main/proto/*.proto

proto: src/main/proto/grpc_controller.proto src/main/proto/saturnbot.proto src/main/proto/grpc_stdio.proto

src/main/proto/grpc_controller.proto:
	mkdir -p src/main/proto
	curl -L -o src/main/proto/grpc_controller.proto --silent --fail https://raw.githubusercontent.com/hashicorp/go-plugin/v1.6.0/internal/plugin/grpc_controller.proto
	echo 'option java_package = "net.saturnbot.plugin.protocol";' >> src/main/proto/grpc_controller.proto

src/main/proto/saturnbot.proto:
	mkdir -p src/main/proto
	curl -L -o src/main/proto/saturnbot.proto --silent --fail https://raw.githubusercontent.com/wndhydrnt/saturn-bot-protocol/$(PROTOCOL_VERSION)/protocol/v1/saturnbot.proto
	echo 'option java_package = "net.saturnbot.plugin.protocol";' >> src/main/proto/saturnbot.proto

src/main/proto/grpc_stdio.proto:
	mkdir -p src/main/proto
	curl -L -o ./src/main/proto/grpc_stdio.proto --silent --fail https://raw.githubusercontent.com/hashicorp/go-plugin/v1.6.0/internal/plugin/grpc_stdio.proto
	echo 'option java_package = "net.saturnbot.plugin.protocol";' >> src/main/proto/grpc_stdio.proto

$(INTEGRATION_TEST_PATH):
	curl -fsSL -o $(INTEGRATION_TEST_PATH) "https://github.com/wndhydrnt/saturn-bot-protocol/releases/download/$(PROTOCOL_VERSION)/$(INTEGRATION_TEST_BIN)"
	chmod +x $(INTEGRATION_TEST_PATH)

test_integration: $(INTEGRATION_TEST_PATH)
	$(INTEGRATION_TEST_PATH) -plugin-path ./integration_test/target/saturn-bot-kotlin-integration-test-latest-jar-with-dependencies.jar -saturn-bot-path $(SATURN_BOT_BIN_PATH)
