import React, { memo, useEffect, useState } from 'react';
import { useLoginUser } from '../../hooks/providers/useLoginUserProvider';
import { IncorrectLogin } from './IncorrectLogin';
import { Avatar, Box, Center, Container, Flex, Heading, StackDivider, Text, Textarea, VStack, useDisclosure } from '@chakra-ui/react';
import { SecondaryButton } from '../atoms/button/SecondaryButton';
import { useTweetRegister } from '../../hooks/useTweetRegister';
import { useTweetsGet } from '../../hooks/useTweetsGet';
import { MenuIconButton } from '../atoms/button/MenuIconButton';
import { MenuDrawer } from '../molecules/MenuDrawer';

export const Tweet: React.FC = memo(() => {
  const [tweet, setTweet] = useState("");
  const handleTweetChange = (event: any) => setTweet(event.target.value);
  const { isOpen, onOpen, onClose } = useDisclosure();

  const { loginUser } = useLoginUser();
  const { loginFlag, userId } = loginUser;
  const { tweetRegister, isLoading } = useTweetRegister();
  const { tweetsGet, isLoadingTweet, tweets } = useTweetsGet();
  const firstTweetsGet = () => tweetsGet();
  useEffect(() => firstTweetsGet(), []);
  const onClickTweetRegister = () => {
    tweetRegister({ tweet, userId });
    setTweet('');
    setTimeout(() => tweetsGet(), 1000);
  };

  return (
    <>
      {loginFlag ? (
        <>
          <Container maxW="md" py={10}>
            <Flex align="center" justify="space-between" mb={6}>
              <Heading as="h1" size="lg">
                Tweeterのクローン
              </Heading>
              <MenuIconButton onOpen={onOpen} />
            </Flex>
            <Box bg="gray.50" p={4} rounded="md" mb={6} alignItems="center">
              <Textarea
                value={tweet}
                onChange={handleTweetChange}
                placeholder="ツイート内容を入力してください"
                mb={4}
              />
              <Center>
                <SecondaryButton
                  isDisabled={tweet === ""}
                  onClick={onClickTweetRegister}
                  isLoading={isLoading || isLoadingTweet}
                >投稿する</SecondaryButton>
              </Center>
            </Box>
            <VStack
              divider={<StackDivider borderColor="gray.200" />}
              spacing={4}
              align="stretch"
            >
              {tweets.map((tweet, index) => (
                <Flex key={index} p={2} bg="white" rounded="md">
                  <Avatar size="sm" name={tweet.authorName} mr={3} />
                  <Box>
                    <Text fontWeight="bold">{tweet.authorName}</Text>
                    <Text fontSize="sm" color="gray.500">{tweet.createdAt}</Text>
                    <Text>{tweet.tweet}</Text>
                  </Box>
                </Flex>
              ))}
            </VStack>
          </Container>
          <MenuDrawer isOpen={isOpen} onClose={onClose} />
        </>
      ) : (<IncorrectLogin />)}
    </>
  );
});
