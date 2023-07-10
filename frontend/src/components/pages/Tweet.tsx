import React, { memo, useState } from 'react';
import { useLoginUser } from '../../hooks/providers/useLoginUserProvider';
import { IncorrectLogin } from './IncorrectLogin';
import { Avatar, Box, Center, Container, Flex, Heading, StackDivider, Text, Textarea, VStack } from '@chakra-ui/react';
import { SecondaryButton } from '../atoms/button/SecondaryButton';
import { useTweetRegister } from '../../hooks/useTweetRegister';
import { PrimaryButton } from '../atoms/button/PrimaryButton';

export const Tweet: React.FC = memo(() => {
  // テストデータ後で消す
  const tweets = [
    { id: 1, tweet: "Just had the most delicious sushi for lunch. 🍣🤤 Highly recommend trying it if you're a sushi lover like me! #foodie #sushi", createdAt: "2023-07-09 20:28:38", authorId: 1, authorName: "t_hanako" }, { id: 2, tweet: "Excited to announce the launch of our new website! 🚀🎉 It's been months of hard work, but it's finally live. Check it out and let us know what you think! #website #launch", createdAt: "2023-07-10 08:35:01", authorId: 2, authorName: "新屋良樹" }
  ];

  const { loginUser } = useLoginUser();
  const { loginFlag, userId } = loginUser;
  const [tweet, setTweet] = useState("");
  const handleTweetChange = (event: any) => setTweet(event.target.value);
  const { tweetRegister, isLoading } = useTweetRegister();
  const onClickTweetRegister = () => {
    tweetRegister({ tweet, userId });
    setTweet('');
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
                  isLoading={isLoading}
                >投稿する</SecondaryButton>
              </Center>
            </Box>
            {/* 投稿されたTweet */}
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
        </>
      ) : (<IncorrectLogin />)}
    </>
  );
});
