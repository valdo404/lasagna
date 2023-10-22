The plan of lasagna is to build an data toolkit that is able to do:

Batching SQL
- Big data querying on both spark SQL and bigquery, allowing last SQL constructs on both engines, and avoiding vendor lockin
- Inject custom generated data at development time, in order to ease testing
- Static analyis on SQL queries, identifying problems before executing them, and assessing execution plans 
- Build a dynamical DAG suitable for execution for common Schedulers like airflow, but not only. 
- Build integrated data quality checks, pushing data to Data Quality Check system 
- Build integrated lineage analysis, pushing data to Open Lineage specification
- Track data sources and data targets, and allowing dynamical configuration of them, using yaml but not only
- Provide analysis using LLM systems, and advise some refactorings when required.

Streaming
- Integrate with beam but also with ksql, allowing streaming sql constructs, and avoiding vendor lockin
- Inject custom generated data at development time, in order to ease testing
- Static analysis on streaming SQL queries, identifying problems, and previsualising queries on data streams
- Build a Dynamical Streaming DAG, by leveraging beam / kafka streams sinks and sources
- Build integrated data quality check for streaming
- Build integrated lineage analysis for streams
- Track data sources and data targets
- Provide analysis of the whole using LLM systems.

The idea is to replace DBT (data build tool) by a system which also suitable for streaming, but is built with a modern langage like Scala.

But unlike DBT it would also provide lineage, analysis, and data quality. DBT does not do it right.