.PHONY: local-env-start local-env-stop

local-postgres-start:
	docker compose -f local-dev/docker-compose.yml up -d

local-postgres-stop:
	docker compose -f local-dev/docker-compose.yml down
