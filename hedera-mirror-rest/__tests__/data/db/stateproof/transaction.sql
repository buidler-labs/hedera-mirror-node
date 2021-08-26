--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.18
-- Dumped by pg_dump version 12.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
-- The following line is auto generated by pg_dump and there is no option to disable it. Clearing search_path globally
-- will cause the sql queries in our implementation fail (relation not found). So comment it out.
-- SELECT pg_catalog.set_config('search_path', '', false);

--
-- Data for Name: transaction; Type: TABLE DATA; Schema: public; Owner: mirror_node
--

INSERT INTO public.transaction (consensus_ns, type, result, payer_account_id, valid_start_ns, valid_duration_seconds, node_account_id, entity_id, initial_balance, max_fee, charged_tx_fee, memo, transaction_hash, transaction_bytes) VALUES
	(1570800761443132000, 11, 22, 94139, 1570800748313194300, 120, 3, 111146, 70000000000, 100000000, 71563593, '\x437265617465204163636f756e742054657374', '\x2ad4022e2b8bf87b6b07443c514c5b2d2c1276d6bfa32d36568dc773c2e273572fa232c2f9bfc8b9dd7be69f790b41c2', NULL),
	(1570800802783669054, 14, 22, 94139, 1570800790443984000, 120, 3, NULL, 0, 500000000, 253995, '\x67657443727970746f4765744163636f756e74496e666f', '\xafe1b151dc512a42c52c3c51d35738eed16b1a99db24615d4c3ff7b66377adcf62412beb888d8f0d4302a96c53ee1e78', NULL),
	(1570800802870345000, 11, 22, 94139, 1570800790519782600, 120, 3, 111147, 70000, 100000000, 40678442, '\x437265617465204163636f756e742054657374', '\x05f927426a9772e8ce67409768c1a6e34e3d8a5f4f74e35cb7533367c530ee15677412f2a790f25398a9f8c9cc99a754', NULL),
	(1570800804165773000, 14, 22, 94139, 1570800791811610700, 120, 3, NULL, 0, 500000000, 253995, '\x67657443727970746f4765744163636f756e74496e666f', '\x7f27235df688b758f7e73308e176002f4ac77d8a6a55c2a1fe1588b7199bae993ab10cb50cd0b392f0b10a62a559e2f1', NULL),
	(1570800804212804002, 11, 22, 94139, 1570800791865368000, 120, 3, 111148, 70000, 100000000, 40678442, '\x437265617465204163636f756e742054657374', '\xc63f7a2d3cc9e720e2457b383af310097b93349295aaceda2fcb1cd1a1a4b60ed5f0b7d4605f59c9692c440b953e9409', NULL),
	(1570800805526889000, 14, 22, 94139, 1570800793172652300, 120, 3, NULL, 0, 500000000, 253995, '\x67657443727970746f4765744163636f756e74496e666f', '\xc77b97f8f2f01636027a74ab7c19a506638de68149b47c7e8a38424e9a617f827fbb51afea858ac936bedad3c7c84116', NULL),
	(1570800805941171000, 17, 22, 111146, 1570800793441583600, 120, 3, 111149, 0, 700000000, 608622391, '\x46696c65437265617465', '\x9a8f13e9989462a2028bc5e3946d0f3d556d4ccfc65f2afa55b94c535823ea8a27caf630de85ae22303a73b7a0ded98d', NULL),
	(1570800806087721000, 14, 22, 111146, 1570800793706899200, 120, 3, NULL, 0, 500000000, 887187, '\x5472616e73666572', '\xd2c3cfc39dd81aac6acbed09e6af6628d910dd6557ffdc483f8deb943193946fa900e3a6a40263479db49938d6976896', NULL),
	(1570800806199552000, 14, 22, 111146, 1570800793853991500, 120, 3, NULL, 0, 500000000, 582379, '\x6765745472616e73616374696f6e5265636f7264', '\x23034082728a39d0f65a9a66666b08cbefe7e0304494c09d2a1812e39feffd04b439f32d3f3148553898ce16fd386959', NULL),
	(1570800806503402000, 14, 22, 111146, 1570800794059669700, 120, 3, NULL, 0, 500000000, 582123, '\x66696c65476574496e666f5175657279', '\x0e4d28ca546aa84c199c4d7c6a5a6c3f67990c55cf38803f7edd5bc0f4f78a3fdd27b380509cb84678542fd947502904', NULL),
	(1570800806602793001, 14, 22, 111146, 1570800794253943600, 120, 3, NULL, 0, 500000000, 582570, '\x67657443727970746f4765744163636f756e74496e666f', '\x1c59bea237d3f8e639cc015476f3a355378a42bf9382aefda6e6d95433d9ab85b7e4bac57a480f82fc6f0b1c6169b8ed', NULL),
	(1570800806715760000, 14, 22, 111146, 1570800794356657900, 120, 3, NULL, 0, 500000000, 581996, '\x46696c65476574436f6e74656e74', '\x11e421afb58e3dc83d45e759fa29bc6cff738a3d35bd5a3971a4f3969d4fcb05988a01a4f4b6a48cfd7ef203eb74001d', NULL),
	(1570800806862677000, 17, 22, 111146, 1570800794425339300, 120, 3, 111150, 0, 700000000, 653540869, '\x46696c65437265617465', '\xd4964bfe66d311c60717c733e39c1e4b4aae38138e0f583fef9ae3844eb95772862f5f467ebb96efd06e80ba0b0059da', NULL),
	(1570800807283660000, 15, 22, 111146, 1570800794917919500, 30, 3, 111146, 0, 500000000, 1256006, '\x557064617465204163636f756e74', '\x1c2b27dbf2d093fa353fc444e0a3ed1ac932518a37d2b03509f8a06039d454df82aeba5f35d7a1fea5babaca298a4288', NULL),
	(1570800807415457000, 14, 22, 111146, 1570800794982781600, 120, 3, NULL, 0, 500000000, 582123, '\x66696c65476574496e666f5175657279', '\xdaaba9efec3f3fff2e0a72058977002733f382d7b067cdc0532bb1ee144deba15b55b2336f4a5cd4a247e2161a9a0d65', NULL),
	(1570800807436548000, 14, 22, 111146, 1570800795099464700, 120, 3, NULL, 0, 500000000, 582506, '\x67657454785265636f726442794163636f756e744964', '\x8bc6cb8a55d4ed3fc41c4be1909a8202f53cdbee73ff76409f849124de7b7a46b0471c2eb249cc8560e25f68500771e0', NULL),
	(1570800807612881001, 14, 22, 111146, 1570800795234110500, 120, 3, NULL, 0, 500000000, 582570, '\x67657443727970746f4765744163636f756e74496e666f', '\xcbd94993150f0b89e2b71cebc90b32005ed18a9853e2fa67663d0456292d11f5b6c7362cad0e91698af68fcd2a006cc5', NULL),
	(1570800807612881003, 14, 22, 111146, 1570800795248073200, 120, 3, NULL, 0, 500000000, 581996, '\x46696c65476574436f6e74656e74', '\x142b1bd221601b45bd9612fc99f1e2c6785cd95af564933fcbbaaef75251608426608369fa5dcddd27e405a7f8673372', NULL),
	(1570800807671158000, 11, 22, 111146, 1570800795320880100, 120, 3, 111151, 70000, 100000000, 72879614, '\x437265617465204163636f756e742054657374', '\x777c2f25ff43c346bddf76f9a43c81e81f307f6db2287623f7741e0ebe743c204ec3e8927c3e223c08120c5c16caa07f', NULL),
	(1570800807974627001, 14, 22, 111146, 1570800795549046900, 120, 3, NULL, 0, 500000000, 582123, '\x66696c65476574496e666f5175657279', '\x3de4e8bd8a4d3311249487933d118f67fae836b428c6e4439aad063e119ae72b2465401195e1910efc0734ba04efedfa', NULL),
	(1570800808938394000, 14, 22, 94139, 1570800796611089900, 120, 3, NULL, 0, 500000000, 253995, '\x67657443727970746f4765744163636f756e74496e666f', '\xc1cd09ebf037e4742aa8c433d0da4de3eca5d15f43a8878ccc639880e0e1e3ad9e9334b8632a84954486b1fe22016acb', NULL),
	(1570800809191406000, 12, 22, 94139, 1570800796848632500, 100, 3, 111146, 0, 500000000, 35690537, '\x43727970746f2044656c657465', '\x3ad857a5f88fe7cc8e16e4bac51171b04690dcaa00dde4d20a87302b861f7e04fbe1361f0a353dfa9dfe1e87c9e707ab', NULL),
	(1570800809446072000, 12, 22, 94139, 1570800797110597300, 100, 3, 111147, 0, 500000000, 20349060, '\x43727970746f2044656c657465', '\x1405045aeade32e2ec76e83b121c13f9ddc0da6fa64218d26320af4388bdc7c353a55fc9ae715c6ff70579435449970d', NULL),
	(1570800809699397001, 12, 22, 94139, 1570800797372688700, 100, 3, 111148, 0, 500000000, 20349060, '\x43727970746f2044656c657465', '\x26905ea8d5a061e1c1f5cccbfa2e0a26ad813b71869e92827419bd148e3434458ad8334e3498804d1b8be31db13acba8', NULL),
	(1570800945120768000, 14, 22, 111152, 1570800932629451400, 120, 3, NULL, 0, 500000000, 582314, '\x66696c65476574496e666f5175657279', '\x82ce51cf950992ebed9a92a9dc8824a7927841f6c6f4ac97d1df15b724866a27c45e5ba22c21a714cd4520f232503020', NULL),
	(1570803140026969000, 14, 22, 94139, 1570803127678422600, 120, 3, NULL, 0, 500000000, 253995, '\x67657443727970746f4765744163636f756e74496e666f', '\xa76eea07805eec6844f98d669261a78e2c5dbcfa3bb3ca4985e31eed27210cfef56dd566b5740c575eac064cd527e208', NULL),
	(1570805375574047000, 14, 10, 111699, 1570805363235017300, 120, 3, NULL, 0, 500000000, 1495810, '\x5472616e73666572', '\x50e439a127eb96b1e1b2754d33c8be693e4cbc5c35c4bbd92df1fe55923a69dcb16419209dd034e15315b9c33940bfc0', NULL);

--
-- schedulescreate transaction in the first record file and the scheduled cryptotransfer transaction in another record file
--

INSERT INTO public.transaction (consensus_ns, type, result, payer_account_id, valid_start_ns, valid_duration_seconds, node_account_id, entity_id, initial_balance, max_fee, charged_tx_fee, memo, transaction_hash, transaction_bytes, scheduled) VALUES
    (1570800762443132000, 42, 22, 94139, 1570800762443131000, 120, 3, NULL, 0, 500000000, 253995, '\x67657443727970746f4765744163636f756e74496e666f', '\xfae15b11dc512a42c52c3c51d35738eed16b1a99db24615d4c3007b22159cdaf62412beb888d8f9d4314a96c53ef1e66', NULL, false),
	(1570803140026969100, 14, 22, 94139, 1570800762443131000, 120, 3, NULL, 0, 500000000, 253995, '\x67657443727970746f4765744163636f756e74496e666f', '\xafe1b151dc512a42c52c3c51d35738eed16b1a99db24615d4c3ff7b66377adcf62412beb888d8f9d4314a96c53ef1e78', NULL, true);

--
-- PostgreSQL database dump complete
--

