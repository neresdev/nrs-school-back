---
name: spring-endpoint-with-integration-tests
description: Create complete Spring Boot endpoints with integration tests, Cucumber features, step definitions, and ensure all tests and Checkstyle pass.
---

# Objective

Whenever a new backend endpoint is requested, implement the complete feature following the project's architecture and quality standards.

## Architecture

Follow the existing project structure and conventions.

Reuse existing implementations whenever possible.

Do not introduce new architectural patterns if an equivalent solution already exists.

Implement all necessary layers, including (when applicable):

- Controller
- Service
- Service Implementation
- Repository
- Entity
- DTOs
- Mapper
- Validation
- Exception handling
- Configuration
- Security configuration
- Database migration
- Any additional required classes

Only create files that are actually necessary.

## API Standards

Follow existing REST conventions used by the project.

Use appropriate HTTP methods.

Return proper HTTP status codes.

Validate request payloads.

Return standardized error responses.

Reuse existing exception handlers.

## Code Quality

Follow:

- Clean Code
- SOLID
- DRY
- KISS

Use descriptive names.

Keep methods focused on a single responsibility.

Avoid duplicated logic.

Reuse existing utilities whenever possible.

Do not leave TODOs or unfinished implementations.

## Integration Tests

Every new endpoint must include complete integration tests.

Create a new Cucumber feature file under:

test/resources/features/

The feature file must contain realistic scenarios covering:

- Success cases
- Validation failures
- Unauthorized requests (when applicable)
- Resource not found
- Business rule violations
- Edge cases when appropriate

## Step Definitions

Create all required Step Definition classes under:

test/java/com/nrs/school/back/steps/

Reuse existing steps whenever possible.

Only create new step definitions when no reusable implementation exists.

Avoid duplicated step definitions.

## Test Quality

Integration tests must be complete and executable.

Ensure:

- Clear scenario names
- Readable Given / When / Then structure
- Proper assertions
- Independent scenarios
- Deterministic execution

## Final Validation

After implementing the endpoint:

1. Execute all integration tests.

2. If any integration test fails:

- Identify the root cause.
- Fix the implementation.
- Execute the tests again.

Repeat until all integration tests pass.

3. Execute Checkstyle.

If any Checkstyle violation exists:

- Fix every violation.
- Re-run Checkstyle.

Repeat until there are no Checkstyle violations.

## Completion Criteria

The task is only complete when:

- The endpoint is fully implemented.
- All required classes have been created.
- All Cucumber feature files have been created.
- All Step Definition classes have been created.
- Every integration test passes.
- Checkstyle reports zero violations.
- The implementation follows the project's existing architecture and coding standards.

Never consider the task complete before all these conditions are satisfied.

## DTO Policy

Never expose JPA entities through the API.

Every endpoint must return a controlled Response DTO.

Requirements:

- Never return Entity objects directly.
- Never expose internal database fields.
- Never expose lazy-loaded relationships.
- Never expose sensitive information.
- Create dedicated Request and Response DTOs whenever appropriate.
- Reuse existing DTOs only if they fully satisfy the endpoint requirements.
- Use the project's existing mapper implementation (MapStruct or equivalent) whenever available.
- Keep DTOs focused on the endpoint contract rather than the database model.
- Response DTOs should expose only the data required by the client.
- Request DTOs should contain only the fields accepted by the endpoint.

Every Controller must communicate exclusively through DTOs.

Flow:

Request DTO
→ Controller
→ Service
→ Entity
→ Repository
→ Entity
→ Mapper
→ Response DTO

The API contract must always be independent from the persistence model.
