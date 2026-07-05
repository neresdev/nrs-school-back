.PHONY: local-env-start local-env-stop

local-env-start:
	docker compose -f local-dev/docker-compose.yml up -d

local-env-stop:
	docker compose -f local-dev/docker-compose.yml down
