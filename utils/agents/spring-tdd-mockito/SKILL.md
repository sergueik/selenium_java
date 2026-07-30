---
name: spring-tdd-mockito
version: 1.0.0
description: |
  TDD (Test-Driven Development) skill with Mockito for Spring Boot.
  Guides the Red-Green-Refactor cycle for writing tests first.

triggers:
  - "write test"
  - "tdd"
  - "mockito"
  - "unit test"
  - "test first"
---

# Spring Boot TDD with Mockito

## TDD Cycle: Red → Green → Refactor

```
┌─────────────────────────────────────────────────────────────┐
│                     TDD WORKFLOW                             │
│                                                              │
│   ┌───────┐      ┌───────┐      ┌──────────┐               │
│   │  RED  │ ───→ │ GREEN │ ───→ │ REFACTOR │ ───┐          │
│   │       │      │       │      │          │    │          │
│   │ Write │      │ Write │      │ Improve  │    │          │
│   │ Test  │      │ Code  │      │ Code     │    │          │
│   └───────┘      └───────┘      └──────────┘    │          │
│       ↑                                          │          │
│       └──────────────────────────────────────────┘          │
└─────────────────────────────────────────────────────────────┘
```

## Mockito Annotations

| Annotation | Purpose |
|------------|---------|
| `@Mock` | Create mock object |
| `@InjectMocks` | Inject mocks into class under test |
| `@Spy` | Partial mock - real methods + mock behavior |
| `@Captor` | Capture arguments passed to mock |
| `@MockBean` | Mock bean in Spring context |

## Test Template

```java
@ExtendWith(MockitoExtension.class)
class ServiceTest {

    @Mock
    private Repository repository;

    @InjectMocks
    private Service service;

    @Test
    @DisplayName("Should do something when condition is met")
    void shouldDoSomethingWhenConditionIsMet() {
        // Given (Arrange)
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        // When (Act)
        var result = service.doSomething(1L);

        // Then (Assert)
        assertThat(result).isNotNull();
        verify(repository).findById(1L);
    }
}
```

## Best Practices

1. **One assertion per test** - Focus on single behavior
2. **Descriptive test names** - Use `@DisplayName`
3. **AAA Pattern** - Arrange, Act, Assert
4. **Test behavior, not implementation**
5. **Fast tests** - Mock external dependencies
6. **Isolated tests** - No shared state



origin: https://github.com/majiayu000/claude-skill-registry/blob/main/skills/data/spring-tdd-mockito/SKILL.md
