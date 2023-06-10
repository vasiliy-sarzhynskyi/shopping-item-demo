# Functionality
<ul>
    <li>Controller, Service and Repository layers for basic CRUD operations by shopping items</li>
    <li>Separation into public and admin endpoints</li>
    <li>Admin endpoints are restricted only for authenticated user (with basic login name and password as `admin`)</li>
    <li>Caching on service layer</li>
    <li>Swagger provided by link `http://localhost:8080/swagger-ui.html`</li>
    <li>OpenAPI docs provided by links `http://localhost:8080/v3/api-docs/public-api` and `http://localhost:8080/v3/api-docs/admin-api`</li>
    <li>Caching on service layer</li>
    <li>HTTP tracing report is sent into Kafka topic `shopping_item_http_trace_reporting`</li>
</ul>

# Launch Preconditions
In order to launch application, make sure that you have running Kafka broker on your machine, or need to update configuration property `KAFKA_URL` from 
`application.yml`. Also, topic `shopping_item_http_trace_reporting` should be created in advance, there we will see all HTTP requests reporting by invoked API endpoints.

# Functional Tests
Functional tests use embedded test containers for Kafka messaging system and MariaDB database. Test class `ExecuteEndpointsWithKafkaTraceReportingFunctionalTest` invokes endpoints and check what exactly we have in Kafka topic as a result.

# OpenAPI docs

## Public API
```json

{
  "openapi": "3.0.1",
  "info": {
    "title": "Shopping Items Demo Service",
    "version": "0.0.1-SNAPSHOT"
  },
  "servers": [
    {
      "url": "http://localhost:8082",
      "description": "Generated server url"
    }
  ],
  "paths": {
    "/shopping-items/{itemId}": {
      "get": {
        "tags": [
          "shopping-item-controller"
        ],
        "operationId": "getShoppingItem",
        "parameters": [
          {
            "name": "itemId",
            "in": "path",
            "required": true,
            "schema": {
              "type": "integer",
              "format": "int64"
            }
          }
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/ShoppingItemResponseDto"
                }
              }
            }
          }
        }
      },
      "put": {
        "tags": [
          "shopping-item-controller"
        ],
        "operationId": "updateShoppingItem",
        "parameters": [
          {
            "name": "itemId",
            "in": "path",
            "required": true,
            "schema": {
              "type": "integer",
              "format": "int64"
            }
          }
        ],
        "requestBody": {
          "content": {
            "application/json": {
              "schema": {
                "$ref": "#/components/schemas/ShoppingItemRequestDto"
              }
            }
          },
          "required": true
        },
        "responses": {
          "200": {
            "description": "OK"
          }
        }
      },
      "delete": {
        "tags": [
          "shopping-item-controller"
        ],
        "operationId": "deleteShoppingItem",
        "parameters": [
          {
            "name": "itemId",
            "in": "path",
            "required": true,
            "schema": {
              "type": "integer",
              "format": "int64"
            }
          }
        ],
        "responses": {
          "200": {
            "description": "OK"
          }
        }
      }
    },
    "/shopping-items": {
      "get": {
        "tags": [
          "shopping-item-controller"
        ],
        "operationId": "getAllShoppingItems",
        "parameters": [
          {
            "name": "page",
            "in": "query",
            "required": true,
            "schema": {
              "minimum": 0,
              "type": "integer",
              "format": "int32"
            }
          },
          {
            "name": "size",
            "in": "query",
            "required": true,
            "schema": {
              "minimum": 1,
              "type": "integer",
              "format": "int32"
            }
          }
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/PageableSearchResultDtoShoppingItemResponseDto"
                }
              }
            }
          }
        }
      },
      "post": {
        "tags": [
          "shopping-item-controller"
        ],
        "operationId": "createShoppingItem",
        "requestBody": {
          "content": {
            "application/json": {
              "schema": {
                "$ref": "#/components/schemas/ShoppingItemRequestDto"
              }
            }
          },
          "required": true
        },
        "responses": {
          "201": {
            "description": "Created",
            "content": {
              "*/*": {
                "schema": {
                  "type": "integer",
                  "format": "int64"
                }
              }
            }
          }
        }
      }
    },
    "/shopping-items/bulk-create": {
      "post": {
        "tags": [
          "shopping-item-controller"
        ],
        "operationId": "bulkCreateShoppingItems",
        "requestBody": {
          "content": {
            "application/json": {
              "schema": {
                "$ref": "#/components/schemas/BulkShoppingItemsRequestDto"
              }
            }
          },
          "required": true
        },
        "responses": {
          "201": {
            "description": "Created",
            "content": {
              "*/*": {
                "schema": {
                  "type": "array",
                  "items": {
                    "type": "integer",
                    "format": "int64"
                  }
                }
              }
            }
          }
        }
      }
    },
    "/shopping-items/search/by-name": {
      "get": {
        "tags": [
          "shopping-item-controller"
        ],
        "operationId": "searchShoppingItemByName",
        "parameters": [
          {
            "name": "name",
            "in": "query",
            "required": true,
            "schema": {
              "type": "string"
            }
          }
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/ShoppingItemResponseDto"
                }
              }
            }
          }
        }
      }
    }
  },
  "components": {
    "schemas": {
      "ShoppingItemRequestDto": {
        "required": [
          "name"
        ],
        "type": "object",
        "properties": {
          "name": {
            "type": "string"
          },
          "description": {
            "type": "string"
          },
          "version": {
            "type": "integer",
            "format": "int64"
          }
        }
      },
      "BulkShoppingItemsRequestDto": {
        "required": [
          "items"
        ],
        "type": "object",
        "properties": {
          "items": {
            "type": "array",
            "items": {
              "$ref": "#/components/schemas/ShoppingItemRequestDto"
            }
          }
        }
      },
      "PageableSearchResultDtoShoppingItemResponseDto": {
        "type": "object",
        "properties": {
          "totalPages": {
            "type": "integer",
            "format": "int32"
          },
          "totalElements": {
            "type": "integer",
            "format": "int64"
          },
          "data": {
            "type": "array",
            "items": {
              "$ref": "#/components/schemas/ShoppingItemResponseDto"
            }
          }
        }
      },
      "ShoppingItemResponseDto": {
        "type": "object",
        "properties": {
          "id": {
            "type": "integer",
            "format": "int64"
          },
          "name": {
            "type": "string"
          },
          "description": {
            "type": "string"
          },
          "createdTimestamp": {
            "type": "integer",
            "format": "int64"
          },
          "updatedTimestamp": {
            "type": "integer",
            "format": "int64"
          },
          "version": {
            "type": "integer",
            "format": "int64"
          }
        }
      }
    }
  }
}
```

## Admin API
```json

{
  "openapi": "3.0.1",
  "info": {
    "title": "Shopping Items Demo Service",
    "version": "0.0.1-SNAPSHOT"
  },
  "servers": [
    {
      "url": "http://localhost:8082",
      "description": "Generated server url"
    }
  ],
  "paths": {
    "/admin/cached-items": {
      "get": {
        "tags": [
          "admin-controller"
        ],
        "operationId": "getAllCachedShoppingItems",
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/ShoppingItemsResponseDto"
                }
              }
            }
          }
        }
      }
    }
  },
  "components": {
    "schemas": {
      "ShoppingItemResponseDto": {
        "type": "object",
        "properties": {
          "id": {
            "type": "integer",
            "format": "int64"
          },
          "name": {
            "type": "string"
          },
          "description": {
            "type": "string"
          },
          "createdTimestamp": {
            "type": "integer",
            "format": "int64"
          },
          "updatedTimestamp": {
            "type": "integer",
            "format": "int64"
          },
          "version": {
            "type": "integer",
            "format": "int64"
          }
        }
      },
      "ShoppingItemsResponseDto": {
        "type": "object",
        "properties": {
          "items": {
            "type": "array",
            "items": {
              "$ref": "#/components/schemas/ShoppingItemResponseDto"
            }
          }
        }
      }
    }
  }
}

```
